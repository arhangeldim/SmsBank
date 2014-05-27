package arhangel.dim.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import arhangel.dim.R;
import arhangel.dim.entity.Transaction;

public class TransactionDetailsDialog extends DialogFragment implements View.OnClickListener {

    private final static String TAG = "TransactionDetailsDialog";

    private String rawMessageBody;
    private Transaction transaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rawMessageBody = getArguments().getString(MainActivity.BUNDLE_MESSAGE_BODY_KEY);
        transaction = getArguments().getParcelable(MainActivity.BUNDLE_TRANSACTION_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Transaction details");

        View v = inflater.inflate(R.layout.transaction_details_dialog, null);
        v.findViewById(R.id.btnTrDetailsOk).setOnClickListener(this);

        TextView tvCardNumber = (TextView) v.findViewById(R.id.tvTrDetailsCardNumber);
        TextView tvAmount = (TextView) v.findViewById(R.id.tvTrDetailsAmount);
        TextView tvCategory = (TextView) v.findViewById(R.id.tvTrDetailsCategory);
        TextView tvDetails = (TextView) v.findViewById(R.id.tvTrDetailsRawMessage);

        tvCardNumber.setText(transaction.getCardNumber());
        tvAmount.setText(transaction.getAmount() + transaction.getCurrency().toString());
        tvCategory.setText(transaction.getCategory());
        tvDetails.setText(rawMessageBody);

        return v;
    }

    public void onClick(View v) {
        Log.d(TAG, "TransactionDetailsDialog dialog button pressed");
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}