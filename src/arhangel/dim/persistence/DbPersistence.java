package arhangel.dim.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import arhangel.dim.entity.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.List;

/**
 *
 */
public class DbPersistence implements Persistence {

    public static String TAG = "DbPersistence";

    // default db version
    public int dbVersion = 1;

    private DbHelper dbHelper;
    private Context context;

    public static final String TABLE_NAME = "Transactions";

    public static final String ID_KEY = "id";
    public static final String MESSAGE_ID_KEY = "message_id";
    public static final String AMOUNT_KEY = "amount";
    public static final String CURRENCY_KEY = "currency";
    public static final String DESCRIPTION_KEY = "description";
    public static final String CARD_NUMBER_KEY = "card_number";
    public static final String ACCOUNT_ID_KEY = "account_id";
    public static final String IS_PUT_KEY = "is_put";
    public static final String CATEGORY_KEY = "category";


    public DbPersistence(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    @Override
    public List<Transaction> loadTransaction() {

        Log.i(TAG, "Loading transactions...");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Collection<Transaction> transactions = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Transaction t = new Transaction();
                t.setMessageId(cursor.getInt(cursor.getColumnIndex(ID_KEY)));
                t.setAmount(new BigDecimal(cursor.getString(cursor.getColumnIndex(AMOUNT_KEY))));
                t.setCurrency(Currency.getInstance(cursor.getString(cursor.getColumnIndex(CURRENCY_KEY))));
                t.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION_KEY)));
                t.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY_KEY)));
                t.setCardNumber(cursor.getString(cursor.getColumnIndex(CARD_NUMBER_KEY)));
                t.setPut(cursor.getInt(cursor.getColumnIndex(IS_PUT_KEY)) > 0);
                transactions.add(t);
            }

        }

        return null;
    }

    @Override
    public void storeTransactions(List<Transaction> transactions) {
        Log.i(TAG, "Storing transactions ...");

        ContentValues content = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (Transaction t : transactions) {
            content.put(MESSAGE_ID_KEY, t.getMessageId());
            content.put(AMOUNT_KEY, t.getAmount().toString());
            content.put(CURRENCY_KEY, t.getCurrency().toString());
            content.put(DESCRIPTION_KEY, t.getDescription());
            content.put(CATEGORY_KEY, t.getCategory());
            content.put(CARD_NUMBER_KEY, t.getCardNumber());
            //content.put(ACCOUNT_ID_KEY, t.getAccount().getId());
            content.put(IS_PUT_KEY, t.isPut() ? 1 : 0);
            db.insert(TABLE_NAME, null, content);
        }
        dbHelper.close();
    }


    class DbHelper extends SQLiteOpenHelper {


        private static final String CREATE_TRANSACTION_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID_KEY + " integer primary key autoincrement, " +
                        MESSAGE_ID_KEY + " integer, " +
                        AMOUNT_KEY + " text, " +
                        CURRENCY_KEY + " text, " +
                        DESCRIPTION_KEY + " text, " +
                        CATEGORY_KEY + " text, " +
                        CARD_NUMBER_KEY + " text, " +
                        ACCOUNT_ID_KEY + " integer, " +
                        IS_PUT_KEY + " integer" +
                        ");";


        public DbHelper(Context context) {
            super(context, "TransactionsDb", null, dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "Creating database: " + CREATE_TRANSACTION_TABLE);
            db.execSQL(CREATE_TRANSACTION_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
