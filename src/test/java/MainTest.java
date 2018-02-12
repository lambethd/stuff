import control.Settings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class MainTest {

    WebDriver driver;

    @Before
    public void setup() {
        System.setProperty(Settings.DRIVER_TYPE, Settings.DRIVER_LOCATION);
        driver = new ChromeDriver();
    }

    @After
    public void cleanup() {
        if (driver != null) {
            driver.close();
            driver = null;
        }
    }

    @Test
    public void googleHasCorrectTitle() throws InterruptedException {
        driver.get("http://www.google.co.uk");
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        assertEquals("Google webpage should have title of Google", "Google", driver.getTitle());
    }

    @Test
    public void canGetToBathSUScore() throws InterruptedException {
        driver.get(Settings.WEB_ADDRESS);
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        assertEquals("Title should be SCORE", "SCORE", driver.getTitle());
    }
}