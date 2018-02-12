package control;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static control.Settings.DRIVER_LOCATION;
import static control.Settings.DRIVER_TYPE;

public class SurveyMonkey {
    WebDriver driver;
    private static final String SURVEY_MONKEY_ADDRESS = "https://www.surveymonkey.co.uk/r/Y5329NT";

    public static void main(String[] args) throws InterruptedException {
        new SurveyMonkey();
    }

    public SurveyMonkey() throws InterruptedException {
        System.setProperty(DRIVER_TYPE, DRIVER_LOCATION);
        driver = new ChromeDriver();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        for (int i = 0; i < 400; i++) {
            driver.manage().deleteAllCookies();
            driver.get(SURVEY_MONKEY_ADDRESS);
            //Thibault
            WebElement button = driver.findElement(By.xpath("//*[@id=\"question-field-223551490\"]/fieldset/div/div/div[3]/div/label/span[1]"));
            button.click();
            //Kit
            button = driver.findElement(By.xpath("//*[@id=\"question-field-223551491\"]/fieldset/div/div/div[1]/div/label/span[1]"));
            button.click();
            //Men's fives
            button = driver.findElement(By.xpath("//*[@id=\"question-field-223551492\"]/fieldset/div/div/div[3]/div/label/span[1]"));
            jse.executeScript("arguments[0].scrollIntoView(true);", button);
            button.click();
            //other
            button = driver.findElement(By.xpath("//*[@id=\"question-field-223551493\"]/fieldset/div/div/div[1]/div/label/span[1]"));
            jse.executeScript("arguments[0].scrollIntoView(true);", button);
            button.click();
            button = driver.findElement(By.xpath("//*[@id=\"question-field-223551494\"]/fieldset/div/div/div[3]/div/label/span[1]"));
            jse.executeScript("arguments[0].scrollIntoView(true);", button);
            button.click();
            button = driver.findElement(By.xpath("//*[@id=\"patas\"]/article/section/form/div[2]/button"));
            jse.executeScript("arguments[0].scrollIntoView(true);", button);
            button.click();
            if (i % 10 == 0) {
                System.out.println("Voted " + i + " times.");
            }
        }
    }
}
