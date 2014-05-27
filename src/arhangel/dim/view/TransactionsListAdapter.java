package arhangel.dim.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import arhangel.dim.R;
import arhangel.dim.entity.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 */
public class TransactionsListAdapter extends BaseAdapter {
    private List<Transaction> transactions;
    private Activity activity;
    private SimpleDateFormat sdf;

    private static final String DATE_FORMAT = "dd.MM.yyyy";

    TransactionsListAdapter(Activity activity, List<Transaction> transactions) {
        this.transactions = transactions;
        this.activity = activity;
        sdf = new SimpleDateFormat(DATE_FORMAT);
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        TransactionItemHolder holder = null;
        if (item == null) {
            item = activity.getLayoutInflater().inflate(
                    R.layout.transaction_item, parent, false);
            holder = new TransactionItemHolder(item);
            item.setTag(holder);
        } else {
            holder = (TransactionItemHolder) item.getTag();
        }

        holder.populateFrom((Transaction) getItem(position));
        return item;
    }

    class TransactionItemHolder {
        private TextView tvAmount;
        private TextView tvDate;
        private TextView tvDescription;
        private TextView tvCategory;

        TransactionItemHolder(View item) {
            tvAmount = (TextView) item.findViewById(R.id.tvAmount);
            tvDate = (TextView) item.findViewById(R.id.tvDate);
            tvDescription = (TextView) item.findViewById(R.id.tvPlace);
            tvCategory = (TextView) item.findViewById(R.id.tvCategory);
        }

        void populateFrom(Transaction transaction) {
            tvAmount.setText(transaction.getAmount().toString());
            tvDescription.setText(transaction.getDescription());
            tvDate.setText(sdf.format(transaction.getDate()));
            tvCategory.setText(transaction.getCategory());
        }
    }
}
