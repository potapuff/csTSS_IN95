package sumdu.tss.in85.model;

import sumdu.tss.in85.helper.Keys;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {

    public static final String LIST_OF_TABLES_SQL =
            """
                    SELECT name FROM sqlite_master
                    WHERE type IN ('table','view') AND name NOT LIKE 'sqlite_%'
                    UNION ALL
                    SELECT name FROM sqlite_temp_master
                    WHERE type IN ('table','view')
                    ORDER BY 1
                    """;

    public static Table asTable(String query) throws SQLException {
        try (var connection = DriverManager.getConnection("jdbc:sqlite:" + Keys.get("DB.NAME"))) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(query);
            return new Table(rs);
        }
    }

}
