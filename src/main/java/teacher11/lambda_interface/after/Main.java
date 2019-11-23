package teacher11.lambda_interface.after;


import java.util.Scanner;

/**
 * Source: github.com/lifeandfree/stream-api
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your salary");
        Integer salary = scanner.nextInt();
        System.out.println("Enter your country");
        scanner.nextLine();
        String country = scanner.nextLine();
        TaxCalculator taxCalculator = null;
        switch (country) {
            case "Russia":
                taxCalculator = (int summ) -> summ * 0.13;
                break;
            case "USA":
                taxCalculator = (summ) -> summ * 0.25;
                break;
            case "Angola":
                taxCalculator = summ -> summ * 0.90;
                break;
        }
        System.out.println(taxCalculator.calculateTax(salary));
    }
}