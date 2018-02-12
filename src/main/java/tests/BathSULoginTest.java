package tests;


import control.Settings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class BathSULoginTest implements InitialTest {
    public boolean runTest(WebDriver driver) {
        driver.get(Settings.WEB_ADDRESS);
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);

        boolean isContinue = true;
        try {
            WebElement element = driver.findElement(By.id("login-start"));
            if (element.getText().equalsIgnoreCase("Sign In")) {
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
            System.out.println("You are logged into thesubath.com");
        } else {
            System.out.println("You are not logged into thesubath.com, please login");
        }
    }
}
