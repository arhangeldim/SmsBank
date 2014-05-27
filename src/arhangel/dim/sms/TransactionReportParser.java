package arhangel.dim.sms;

import arhangel.dim.entity.Transaction;

/**
 *
 */
public interface TransactionReportParser {
    Transaction parse(String message);
}
