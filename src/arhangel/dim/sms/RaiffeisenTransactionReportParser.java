package arhangel.dim.sms;

import android.util.Log;
import arhangel.dim.classifier.BayesClassifier;
import arhangel.dim.entity.Transaction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CALL:
 * [0] Karta *6787;
 * [1] Provedena tranzakcija:5,00EUR;
 * [2] Data:16/07/2013;
 * [3] Mesto: SKYPE +44 870835190;
 * [4] Dostupny Ostatok: 127991,31RUB. Raiffeisenbank',
 * <p/>
 * PUT:
 * [0] Balans vashey karty *6787 popolnilsya 24/01/2014 na:49505,66RUB.
 * [1] Dostupny Ostatok: 49834,88RUR.
 * [2] Raiffeisenbank
 */
public class RaiffeisenTransactionReportParser implements TransactionReportParser {

    private static final String TAG = "RaiffeisenTransactionReportParser";

    private static final String CARD_NUMBER_CALL_PATTERN = "Karta\\s\\*(\\d{4})";

    private static final String MONEY_PATTERN = ".*:\\s*(\\d+[,|.]\\d{2})\\s*(\\w{3})";
    private static final String DATE_PATTERN_1 = "(\\s*\\d{2}\\/\\d{2}\\/\\d{4})";
    private static final String DATE_PATTERN_2 = "(\\s*\\d{2}\\.\\d{2}\\.\\d{4})";
    private static final String PLACE_PATTERN = "\\s*Mesto\\s*:\\s*(.*)";
    private static final String PUT_OPERATION_PATTERN = "Balans vashey karty\\s+\\*(\\d{4})\\s+popolnilsya\\s+" +
            "\\s*(\\d{2}.\\d{2}.\\d{4})\\s+na" + MONEY_PATTERN;


    private static final String DATE_FORMAT_1 = "dd/MM/yyyy";
    private static final String DATE_FORMAT_2 = "dd.MM.yyyy";

    private static final String DATE_OUTPUT_FORMAT = "dd.MM.yyyy";

    BayesClassifier classifier;

    public void setClassifier(BayesClassifier classifier) {
        this.classifier = classifier;
    }

    @Override
    public Transaction parse(String message) {
        if (message.startsWith("Balans")) {
            return parsePut(message);
        } else if (message.startsWith("Karta")) {
            return parseCall(message);
        } else {
            Log.i(TAG, "Undefined message: " + message);
            return null;
        }
    }

    private Transaction parsePut(String messageBody) {
        Transaction t = new Transaction();

        String[] sections = messageBody.split("\\.");
        Log.d(TAG, "PUT message: " + messageBody);
        Log.d(TAG, "Put message sections: " + Arrays.toString(sections));
        if (sections.length < 2) {
            return t;
        }

        t.setPut(true);
        Matcher matcher;
        Pattern pattern = Pattern.compile(PUT_OPERATION_PATTERN);
        matcher = pattern.matcher(sections[0]);
        if (matcher.matches()) {
            t.setCardNumber(matcher.group(1));
            BigDecimal amount = new BigDecimal(matcher.group(3).replace(',', '.'));
            t.setAmount(amount);
            t.setCurrency(Currency.getInstance(matcher.group(4)));

            String dateString = matcher.group(2);
            t.setDate(parseDate(dateString));
        }

        Log.i(TAG, "PUT - " + t.toString());

        return t;
    }

    private Transaction parseCall(String messageBody) {
        String[] sections = messageBody.split(";");

        Log.d(TAG, "Call message sections: " + Arrays.toString(sections));

        Transaction t = new Transaction();
        Matcher matcher;

        t.setPut(false);

        // parse description (place)
        Pattern placePattern = Pattern.compile(PLACE_PATTERN);
        matcher = placePattern.matcher(sections[3]);
        if (matcher.matches()) {
            String place = matcher.group(1);
            t.setDescription(place);
            Log.i("PARSED", place);

            if (place != null && classifier != null) {
                t.setCategory(classifier.getMostRelevant(place));
            }
        }

        // parse card number
        Pattern cardNumberPattern = Pattern.compile(CARD_NUMBER_CALL_PATTERN);
        matcher = cardNumberPattern.matcher(sections[0]);
        if (matcher.matches()) {
            String cardNumber = matcher.group(1);
            t.setCardNumber(cardNumber);
        }

        // parse transaction sum and currency
        Pattern moneyPattern = Pattern.compile(MONEY_PATTERN);
        matcher = moneyPattern.matcher(sections[1]);
        if (matcher.matches()) {
            BigDecimal amount = new BigDecimal(matcher.group(1).replace(',', '.'));
            String currency = matcher.group(2);
            t.setAmount(amount);
            t.setCurrency(Currency.getInstance(currency));
        }

        // parse transaction date
        String dateString = sections[2].split(":")[1];
        t.setDate(parseDate(dateString));


        Log.i(TAG, "CALL - " + t.toString());
        return t;
    }

    private Date parseDate(String dateString) {
        Pattern pattern1 = Pattern.compile(DATE_PATTERN_1);
        Pattern pattern2 = Pattern.compile(DATE_PATTERN_2);
        SimpleDateFormat inputDateFormat = null;
        if (pattern1.matcher(dateString).matches()) {
            inputDateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        } else if (pattern2.matcher(dateString).matches()) {
            inputDateFormat = new SimpleDateFormat(DATE_FORMAT_2);
        }

        try {
            if (inputDateFormat != null) {
                Date date = inputDateFormat.parse(dateString);
                return date;
            }
        } catch (ParseException e) {
            System.out.println(inputDateFormat.toString());
            e.printStackTrace();
        }
        return null;
    }

}
