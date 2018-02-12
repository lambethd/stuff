package tests;

import org.openqa.selenium.WebDriver;

public interface InitialTest {
    boolean runTest(WebDriver driver);

    void printMessage(boolean testResult);
}
