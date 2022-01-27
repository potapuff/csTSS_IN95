package sumdu.tss.in85.controller;

import io.javalin.http.Context;
import sumdu.tss.in85.helper.exception.NotFoundException;
import sumdu.tss.in85.helper.utils.ExceptionUtils;
import sumdu.tss.in85.helper.utils.ParameterizedStringFactory;
import sumdu.tss.in85.model.DBService;
import sumdu.tss.in85.model.Table;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class MainController {
    private static final ParameterizedStringFactory readTable = new ParameterizedStringFactory("select * from :table");
    private static final ParameterizedStringFactory notFoundText = new ParameterizedStringFactory("Table :table not found");

    public static void index(Context context) throws Exception {
        Map<String, Object> model = new HashMap<>();
        Table tableNames = DBService.asTable(DBService.LIST_OF_TABLES_SQL);
        model.put("tables", tableNames);
        context.render("/velocity/index.vm", model);
    }

    public static void show(Context context) {
        Map<String, Object> model = new HashMap<>();
        String tableName = context.pathParam("table");
        try {
            model.put("table", DBService.asTable(readTable.addParameter("table", tableName).toString()));
        } catch (SQLException e) {
            if (ExceptionUtils.isSQLTableNotFound(e)) {
                throw new NotFoundException(notFoundText.addParameter("table", tableName).toString());
            }
        }
        model.put("tableName", tableName);
        context.render("/velocity/show.vm", model);
    }

}
