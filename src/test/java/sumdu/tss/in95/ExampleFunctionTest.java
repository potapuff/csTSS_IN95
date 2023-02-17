package sumdu.tss.in95;

import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sumdu.tss.in95.helper.Keys;
import sumdu.tss.in95.helper.utils.ResourceResolver;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
    main idea:
    - for valid tables form database service should return code 200
    - for valid tables text of page should contain table name
    - for un-existed pages service should return 404 code
    - for sqlite system tables service should return 404 code

    Let's prepare database with tables which name we know

    for this test is enough code and body of http response, so we did not need Selenium

 */
public class ExampleFunctionTest {
    private static Server app = null;

    @BeforeAll
    static void initServer() {
        //file from src/test/resources prepared for this test
        File file = ResourceResolver.getResource("example-functional-test.properties");
        Keys.loadParams(file);

        app = new Server();
        app.start(Integer.parseInt(Keys.get("APP.PORT")));
    }

    @AfterAll
    static void stopServer() {
        app.stop();
        app = null;
    }

    @Test
    public void service_should_return_200_code_for_valid_tables() {
        var listOfValidTableNames = Arrays.asList("first_table", "second_table");
        for (var validTableName : listOfValidTableNames) {
            var response = Unirest.get(app.getBaseUrl() + validTableName).asEmpty();
            assertEquals(200, response.getStatus());
        }
    }

    @Test
    public void service_should_contain_table_name_for_valid_tables() {
        var listOfValidTableNames = Arrays.asList("first_table", "second_table");
        for (var validTableName : listOfValidTableNames) {
            var response = Unirest.get(app.getBaseUrl() + validTableName).asString();
            assertTrue(response.getBody().contains(validTableName));
        }
    }

    @Test
    public void service_should_return_404_code_for_invalid_tables() {
        var listOfUnexistedTableNames = Arrays.asList("unexisted_table", "other_unexisted_table");
        for (var validTableName : listOfUnexistedTableNames) {
            var response = Unirest.get(app.getBaseUrl() + validTableName).asEmpty();
            assertEquals(404, response.getStatus());
        }
    }

    /**
     * this test fail because lack of protection inside app
     */
    @Test
    public void service_should_return_404_code_for_system_tables() {
        var listOfSystemTableNames = Arrays.asList("sqlite_master", "sqlite_sequence");
        for (var validTableName : listOfSystemTableNames) {
            var response = Unirest.get(app.getBaseUrl() + validTableName).asEmpty();
            assertEquals(404, response.getStatus());
        }
    }

}