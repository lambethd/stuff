package tests;

import control.Settings;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class InternetSpeedTest implements InitialTest {

    public boolean runTest(WebDriver driver) {
        long start = System.currentTimeMillis();
        driver.get("http://www.google.co.uk");
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        long end = System.currentTimeMillis();

        System.out.println("Total load time for Google was: " + (end - start) + "ms");
        return end - start < Settings.SPEED_THRESHOLD;
    }

    @Override
    public void printMessage(boolean testResult) {
        if (testResult) {
            System.out.println("Speed test was good!");
        } else {
            System.out.println("Speed test was not good");
        }
    }
}
