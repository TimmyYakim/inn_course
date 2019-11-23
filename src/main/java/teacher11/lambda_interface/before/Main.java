package teacher11.lambda_interface.before;

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
        NonFuncTaxCalc taxCalculator = null;
        switch (country) {
            case "Russia":
                taxCalculator = new NonFuncTaxCalc() {
                    @Override
                    public double calculateTax(int summ) {
                        return summ * 0.13;
                    }
                };
                break;
            case "USA":
                taxCalculator = new NonFuncTaxCalc() {
                    @Override
                    public double calculateTax(int summ) {
                        return summ * 0.25;
                    }
                };
                break;
            case "Angola":
                taxCalculator = new NonFuncTaxCalc() {
                    @Override
                    public double calculateTax(int summ) {
                        return summ * 0.9;
                    }
                };
                break;
        }
    }
}