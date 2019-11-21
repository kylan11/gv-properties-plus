package it.greenvulcano.util.metadata.properties;

import it.greenvulcano.gvesb.j2ee.db.connections.JDBCConnectionBuilder;
import it.greenvulcano.gvesb.utils.ResultSetUtilsPlus;
import it.greenvulcano.util.metadata.PropertiesHandler;
import it.greenvulcano.util.metadata.PropertiesHandlerException;
import it.greenvulcano.util.metadata.PropertyHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @version 0.0.1 21/nov/2019
 * @author Francesco Lauritano
 */
public class GVESBPlusPropertyHandler implements PropertyHandler {
	
    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GVESBPlusPropertyHandler.class);
    private final static List<String> managedTypes = new LinkedList<>();
	
    static {   
       managedTypes.add("sqljson");
     
       Collections.unmodifiableList(managedTypes);
    } 
    
    @Override
	public List<String> getManagedTypes() {		
		return managedTypes;
	}   

    @Override
    public String expand(String type, String str, Map<String, Object> inProperties, Object object,
            Object extra) throws PropertiesHandlerException
    {
        if (type.startsWith("sqljson")) {
            return expandSqlJSONProperties(str, inProperties, object, extra);
        }
        return str;
    }

    private String expandSqlJSONProperties(String str, Map<String, Object> inProperties, Object object,
            Object extra) throws PropertiesHandlerException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlStatement = null;
        Connection conn = null;
        String connName = "";
        boolean intConn = false;
        try {
            if (!PropertiesHandler.isExpanded(str)) {
                str = PropertiesHandler.expand(str, inProperties, object, extra);
            }
            int pIdx = str.indexOf("::");
            if (pIdx != -1) {
                connName = str.substring(0, pIdx);
                sqlStatement = str.substring(pIdx + 2);
                intConn = true;
            }
            else {
                sqlStatement = str;
            }
            if (intConn) {
                conn = JDBCConnectionBuilder.getConnection(connName);
            }
            else if ((extra != null) && (extra instanceof Connection)) {
                conn = (Connection) extra;
            }
            else {
                throw new PropertiesHandlerException("Error handling 'sqljson' metadata '" + str
                        + "', Connection undefined.");
            }
            logger.debug("Executing SQL statement {" + sqlStatement + "} on connection [" + connName + "]");
            ps = conn.prepareStatement(sqlStatement);
            rs = ps.executeQuery();
            return ResultSetUtilsPlus.getResultSetAsJSON(rs);
        }
        catch (Exception exc) {
            logger.warn("Error handling 'sqljson' metadata '" + sqlStatement + "'", exc);
            if (PropertiesHandler.isExceptionOnErrors()) {
                if (exc instanceof PropertiesHandlerException) {
                    throw (PropertiesHandlerException) exc;
                }
                throw new PropertiesHandlerException("Error handling 'sqljson' metadata '" + str + "'", exc);
            }
            return "sqljson" + PROP_START + str + PROP_END;
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (Exception exc) {
                    // do nothing
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                }
                catch (Exception exc) {
                    // do nothing
                }
            }
            if (intConn && (conn != null)) {
                try {
                    JDBCConnectionBuilder.releaseConnection(connName, conn);
                }
                catch (Exception exc) {
                    // do nothing
                }
            }
        }
    }
}
