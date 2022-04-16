package sumdu.tss.in85;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import sumdu.tss.in85.helper.Keys;
import sumdu.tss.in85.helper.utils.ResourceResolver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
   main idea:
     - user logon to site
       * user should see known tables in a list
     - user type text to filter field
       * user should see filtrated tables in a list

    Let's prepare database with tables which name we know

    for this test we have check work of ajax request -> we need execute JavaScript -> we need Browser -> we will use Selenium

    Let's make user UI screenshot after each step

 */
public class ExampleSeleniumBasedFunctionalTest {

    public static Server app = null;
    public static WebDriver driver;
    public static String screenshotsPrefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    @BeforeAll
    static void setupTest() {
        //file from src/test/resources prepared for this test
        File file = ResourceResolver.getResource("example-functional-test.properties");
        Keys.loadParams(file);

        app = new Server();
        app.start(Integer.parseInt(Keys.get("APP.PORT")));

        System.setProperty("webdriver.chrome.driver", "");
        WebDriverManager.chromedriver().setup();
    }

    @AfterAll
    static void stopServer() {
        app.stop();
        app = null;
    }

    @BeforeEach
    void initDriver() {
        //driver = new EdgeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterEach
    void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void listOfTablesOnBaseUrlShouldContainOnlyKnownTables() throws IOException {
        var listOfValidTableNames = Arrays.asList("first_table", "second_table");
        driver.get(app.getBaseUrl());

        String pathName = "screenshots\\TS";
        takeScreenshot("list on base url", 1);
        var list = driver.findElements(By.cssSelector("#explorer li"));
        assertEquals(listOfValidTableNames.size(), list.size(), "Expected and actual list must have same length");
        for (var item : list) {
            assertTrue(listOfValidTableNames.contains(item.getText()), "List should contain all known table names");
        }
    }

    @Test
    public void listOfTablesOnBaseUrlShouldContainOnlyKnownTablesAfterFiltration() throws IOException {
        var listOfValidTableNames = Arrays.asList("first_table");
        driver.get(app.getBaseUrl());
        takeScreenshot("filtration", 0);
        driver.findElement(By.id("filter")).sendKeys("first");
        var list = driver.findElements(By.cssSelector("#explorer li"));
        takeScreenshot("filtration", 1);
        assertEquals(listOfValidTableNames.size(), list.size(), "Expected and actual list must have same length");
        for (var item : list) {
            assertTrue(listOfValidTableNames.contains(item.getText()), "List should contain all known table names");
        }
    }

    private void takeScreenshot(String tc, int step) throws IOException {
        var path = String.format("target\\screenshots\\%s\\%s\\%04d.png", screenshotsPrefix, tc, step);
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(path));
    }

}
