package control;

import inputs.CommandLineInput;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import tests.BathSULoginTest;
import tests.InitialTest;
import tests.InternetConnectivityTest;
import tests.InternetSpeedTest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static control.Settings.*;

public class Main {

    WebDriver driver;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        System.out.println("Driver location is " + DRIVER_LOCATION);
        System.setProperty(DRIVER_TYPE, DRIVER_LOCATION);
        driver = new ChromeDriver();
        boolean skipTests = CommandLineInput.isYesResponse("Skip tests? (Y/N)");
        boolean testResults = true;
        if (!skipTests) {
            testResults = runTests();
        }
        if (skipTests || testResults) {
            System.out.println("Tests have completed successfully, will proceed to next stage");
        } else {
            System.out.println("Tests have failed, closing");
            System.exit(0);
        }

        int ticketsToBuy = CommandLineInput.getIntUserInput("How many tickets would you like me to try and get? (Max 20)");
        long timeToStart = 0;
        try {
            timeToStart = CommandLineInput.getLongUserInputFromDate("What time would you like to start trying to buy tickets? (31/12/2017 23:59:59)");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Time to start is " + timeToStart);
        if (timeToStart == 999) {
            timeToStart = System.currentTimeMillis() + 5000;
        }

        attemptAddToBasket(ticketsToBuy, timeToStart);

        System.out.println("They have been added!!! Enjoy your tickets");
    }

    private int attemptAddToBasket(int ticketsToBuy, long timeToStart) {
        try {
            waitUntilTimeToStart(timeToStart);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!checkForATB(driver)) {
            //Keep trying until its found!!
        }
        return buyTickets(driver, ticketsToBuy);
    }

    private int buyTickets(WebDriver driver, int ticketsToBuy) {
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= ticketsToBuy; i++) {
            WebElement aTB = driver.findElement(By.id(ADD_TO_BASKET_ID));
            try {
                aTB.click();
            } catch (WebDriverException e) {
                e.printStackTrace();
                i--;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total time to click " + ticketsToBuy + " times: " + (endTime - startTime) + "ms");
        return ticketsToBuy;
    }

    private void waitUntilTimeToStart(long timeToStart) throws InterruptedException {
        final long MILLIS_IN_SECOND = 1000;
        final long MILLIS_IN_MINUTE = 60000;
        final long MILLIS_IN_HOUR = 3600000;
        while (timeToStart - System.currentTimeMillis() > MILLIS_IN_HOUR) {
            System.out.println("Waiting an hour");
            Thread.sleep(MILLIS_IN_HOUR);
        }
        while (timeToStart - System.currentTimeMillis() > MILLIS_IN_MINUTE) {
            System.out.println("Waiting a minute");
            Thread.sleep(MILLIS_IN_MINUTE);
        }
        while (timeToStart - System.currentTimeMillis() > MILLIS_IN_SECOND) {
            System.out.println("Waiting a second");
            Thread.sleep(MILLIS_IN_SECOND);
        }
    }

    private boolean checkForATB(WebDriver driver) {
        driver.get(WEB_ADDRESS);
        driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
        try {
            driver.findElement(By.id(ADD_TO_BASKET_ID));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean runTests() {
        List<InitialTest> testList = getTests();
        for (InitialTest test : testList) {
            boolean testResult = test.runTest(driver);
            test.printMessage(testResult);

            boolean isContinue = true;
            if (testResult) {
                System.out.println("Test has passed, continuing");
            } else {
                isContinue = CommandLineInput.isYesResponse(CommandLineInput.getUserInput("Test has failed, would you like to continue anyway? (Y/N)"));
            }

            if (!isContinue) {
                return false;
            }
        }
        return true;
    }

    private List<InitialTest> getTests() {
        List<InitialTest> testList = new ArrayList<>();
        testList.add(new InternetConnectivityTest());
        testList.add(new InternetSpeedTest());
        testList.add(new BathSULoginTest());
        return testList;
    }
}