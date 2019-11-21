package it.greenvulcano.gvesb.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResultSetUtilsPlus {

	public static String getResultSetAsJSON(ResultSet rs) throws SQLException {
		{
			JSONArray json = new JSONArray();
			ResultSetMetaData metadata = rs.getMetaData();
			int numColumns = metadata.getColumnCount();

			while (rs.next()) // iterate rows
			{
				JSONObject obj = new JSONObject(); // extends HashMap
				for (int i = 1; i <= numColumns; ++i) // iterate columns
				{
					String column_name = metadata.getColumnName(i);
					obj.put(column_name, rs.getObject(column_name));
				}
				json.put(obj);
			}
			return json.length() == 1 ? json.get(0).toString() : json.toString();
		}
	}
}
