package teacher11.lambda_interface.after;

/**
 * Inspector.
 *
 * @author Ilya_Sukhachev
 * Source: github.com/lifeandfree/stream-api
 */
public class Inspector {

    private TaxCalculator taxCalculator;

    public Inspector(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }
}