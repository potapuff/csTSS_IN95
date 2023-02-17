package sumdu.tss.in95.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Table {

    public List<String> headers;
    public List<List<Object>> rows;

    public Table(ResultSet rs) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        headers = new ArrayList<>(columns);
        for (int i = 0; i < columns; i++) {
            headers.add(rsmd.getColumnName(i + 1));
        }

        rows = new ArrayList<>(10);
        while (rs.next()) {
            var row = new ArrayList<>(columns);

            for (int i = 0; i < columns; i++) {
                row.add(rs.getObject(i + 1));
            }
            rows.add(row);
        }
    }

    public List<List<Object>> getRows() {
        return rows;
    }

    public List<String> getHeaders() {
        return headers;
    }
}
