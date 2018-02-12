package inputs;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CommandLineInput {

    public static String getUserInput(String message) {
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        return sc.nextLine();
    }

    public static boolean isYesResponse(String response) {
        switch (response) {
            case "y":
            case "Y":
            case "yes":
            case "Yes":
                return true;
        }
        return false;
    }

    public static int getIntUserInput(String message) {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static long getLongUserInputFromDate(String message) throws ParseException {
        System.out.println(message);
        Scanner sc = new Scanner(System.in);
        String stringDate = sc.nextLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dt = LocalDateTime.parse(stringDate, dtf);
        ZonedDateTime zdt = dt.atZone(ZoneOffset.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }
}
