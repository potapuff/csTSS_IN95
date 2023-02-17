package sumdu.tss.in95.model;

import sumdu.tss.in95.helper.Keys;
import sumdu.tss.in95.helper.utils.ParameterizedStringFactory;
import sumdu.tss.in95.helper.utils.ResourceResolver;

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
    public static final ParameterizedStringFactory LIST_OF_TABLES_SQL_WITH_FILTER = new ParameterizedStringFactory(
            "SELECT * FROM (" + LIST_OF_TABLES_SQL+") WHERE UPPER(name) like UPPER('%:filter%')"
    );

    public static Table asTable(String query) throws SQLException {
        var file = ResourceResolver.getResource(Keys.get("DB.NAME"));
        if (file == null) {
            throw new RuntimeException("Database file " + Keys.get("DB.NAME") + " not found");
        }
        try (var connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath())) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(query);
            return new Table(rs);
        }
    }

}
