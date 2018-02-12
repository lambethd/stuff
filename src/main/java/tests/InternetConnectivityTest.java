package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class InternetConnectivityTest implements InitialTest {

    public boolean runTest(WebDriver driver) {
        driver.get("http://www.google.co.uk");
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        boolean isContinue = true;
        try {
            WebElement element = driver.findElement(By.cssSelector("#main-message > h1"));
            if (element.getText().equalsIgnoreCase("There is no Internet connection")) {
                isContinue = false;
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            isContinue = true;
        }
        return isContinue;
    }

    @Override
    public void printMessage(boolean testResult) {
        if (testResult) {
            System.out.println("Internet is connected!");
        } else {
            System.out.println("Internet appears to not be connected");
        }
    }
}
