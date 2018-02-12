package control;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static control.Settings.*;

public class SingleTicketBuyer implements Runnable {
    private WebDriver webDriver;
    private long timeToStart;

    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of tickets to buy");
        String line = sc.nextLine();
        int numberOfTickets = Integer.valueOf(line.trim());
        System.out.println(numberOfTickets + " tickets entered.");
        System.out.println("Enter time to start in dd/MM/yyyy hh:mm:ss");
        line = sc.nextLine();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date timeToStartDate = df.parse(line);
        System.setProperty(DRIVER_TYPE, DRIVER_LOCATION);
        for (int i = 0; i < numberOfTickets; i++) {
            WebDriver driver = new ChromeDriver();
            new Thread(new SingleTicketBuyer(driver, timeToStartDate.getTime())).start();
        }
    }

    private SingleTicketBuyer(WebDriver webDriver, long timeToStart) {
        this.webDriver = webDriver;
        this.timeToStart = timeToStart;
    }

    @Override
    public void run() {
        allowLogin();
        waitForStart();
        System.out.println("Loading");
        load(webDriver);
        System.out.println("Beginning buying");
        clickBuy(webDriver);
        System.out.println("Hopefully bought lots of tickets");
    }

    private void allowLogin() {
        webDriver.get(WEB_ADDRESS);
    }

    private void waitForStart() {
        final long MILLIS_IN_SECOND = 1000;
        final long MILLIS_IN_MINUTE = 60000;
        final long MILLIS_IN_HOUR = 3600000;
        try {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void load(WebDriver webDriver) {
        while (true) {
            try {
                webDriver.get(WEB_ADDRESS);
                webDriver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
                webDriver.findElement(By.id(ADD_TO_BASKET_ID));
                return;
            } catch (NoSuchElementException e) {

            }
        }
    }

    private void clickBuy(WebDriver webDriver) {
        WebElement aTB = webDriver.findElement(By.id(ADD_TO_BASKET_ID));
        try {
            aTB.click();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }
}