package teacher11.lambda_interface.after;

/**
 * Source: github.com/lifeandfree/stream-api
 */
@FunctionalInterface
public interface TaxCalculator {
    double calculateTax(int summ);

    default double calc(String i) {
        return 0.0;
    }
}
