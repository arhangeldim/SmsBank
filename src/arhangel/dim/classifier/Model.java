package arhangel.dim.classifier;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class Model {

    private static final String TAG = "Model";

    private Map<String, List<String>> classifiedSamples = new HashMap<>();
    private int sampleCount;
    private Set<String> dictionary = new HashSet<>();

    // How many tokens are in the class
    private Map<String, Integer> lengths = new HashMap<>();

    // ClassName -> (Token -> Frequency)
    private Map<String, Map<String, Integer>> tokenFrq = new HashMap<>();

    public Model(String json) {
        Log.i(TAG, "JSON: " + json);
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("model");
            for (int i = 0; i < array.length(); i++) {
                JSONObject modelRecord = array.getJSONObject(i);
                JSONArray samples = modelRecord.getJSONArray("samples");
                String category = modelRecord.getString("name");
                for (int j = 0; j < samples.length(); j++) {
                    Log.i(TAG, "Add sample: " + samples.getString(j) + " -> " + category);
                    addSample(samples.getString(j), category);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, classifiedSamples.toString());
        createModel();
    }

    private void addSample(String sample, String category) {
        sampleCount++;
        if (classifiedSamples.containsKey(category)) {
            classifiedSamples.get(category).add(sample);
        } else {
            List<String> tmp = new ArrayList<>();
            tmp.add(sample);
            classifiedSamples.put(category, tmp);
        }
    }

    public String[] tokenizeSample(String sample) {
        return sample.split(" ");
    }

    private void createModel() {
        for (Map.Entry<String, List<String>> entry : classifiedSamples.entrySet()) {
            Map<String, Integer> frq = new HashMap<>();
            int wordCount = 0;
            for (String sample : entry.getValue()) {
                String[] tokens = tokenizeSample(sample);
                for (String token : tokens) {
                    if (frq.containsKey(token)) {
                        int tmp = frq.get(token);
                        frq.put(token, tmp + 1);
                    } else {
                        frq.put(token, 1);
                    }
                }
                tokenFrq.put(entry.getKey(), frq);
                dictionary.addAll(Arrays.asList(tokens));
                wordCount += tokens.length;
            }
            lengths.put(entry.getKey(), wordCount);
        }

        Log.i(TAG, "MODEL:\n" + tokenFrq.toString() + "\n" + lengths.toString());
    }

    public int getDictionarySize() {
        return dictionary.size();
    }

    public int getTokensNumber(String category) {
        if (lengths.containsKey(category)) {
            return lengths.get(category);
        } else {
            return 0;
        }
    }

    public int getSamplesNumberInCategory(String category) {
        if (classifiedSamples.containsKey(category)) {
            return classifiedSamples.get(category).size();
        } else {
            return 0;
        }
    }

    public int getSamplesNumber() {
        return sampleCount;
    }

    public int getTokenCountInCategory(String category, String token) {
        if (tokenFrq.containsKey(category)) {
            Map<String, Integer> frq = tokenFrq.get(category);
            if (frq.containsKey(token)) {
                return frq.get(token);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public Set<String> getCategories() {
        return classifiedSamples.keySet();
    }

}
