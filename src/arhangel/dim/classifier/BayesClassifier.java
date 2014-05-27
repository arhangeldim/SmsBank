package arhangel.dim.classifier;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class BayesClassifier {

    private final String TAG = "BayesClassifier";

    private static final double THRESHOLD = 0.05;

    private Model model;

    public BayesClassifier(Model model) {
        this.model = model;
    }

    /*
     * Q = Dc/D + SUM((1+Wc)/(V + L))
     *
     */
    private Map<String, Double> classify(String sample) {
        Map<String, Double> classProbability = new HashMap<>();

        Set<String> categories = model.getCategories();
        double D = model.getSamplesNumber();
        double V = model.getDictionarySize();

        for (String category : categories) {

            // To avoid classifying sample if category doesn't contains any of tokens
            int indicator = 0;

            double Dc = model.getSamplesNumberInCategory(category);
            double L = model.getTokensNumber(category);
            double Q = Math.log(Dc / D);

            for (String token : model.tokenizeSample(sample)) {
                int Wc = model.getTokenCountInCategory(category, token);
                indicator += Wc;
                Q += Math.log((1 + Wc) / (V + L));
            }

            // All tokens are undefined
            if (indicator == 0) {
                classProbability.put(category, Double.NEGATIVE_INFINITY);
            } else {
                classProbability.put(category, Q);
            }
        }
        return classProbability;
    }

    public String getMostRelevant(final String sample) {
        String unifiedSample = sample.toLowerCase();
        Map<String, Double> pValues = classify(unifiedSample);

        Log.i(TAG, "sample=" + sample + ", m=" + pValues);

        double max = Double.NEGATIVE_INFINITY;
        String className = null;
        double probabilitySum = 0;

        for (Map.Entry<String, Double> entry : pValues.entrySet()) {
            probabilitySum += Math.exp(entry.getValue());
            if (entry.getValue() > max) {
                max = entry.getValue();
                className = entry.getKey();
            }
        }

        double p = Math.exp(max) / probabilitySum;
        Log.i(TAG, "SAMPLE: " + sample + "   ->    CLASS: " + className + ", PERCENTAGE: " + p);

        if (max == Double.NEGATIVE_INFINITY || p < THRESHOLD) {
            return "Other";
        } else {
            return className;
        }
    }

}
