package arhangel.dim.persistence;

import arhangel.dim.entity.Transaction;

import java.util.List;

/**
 *
 */
public interface Persistence {

    List<Transaction> loadTransaction();

    void storeTransactions(List<Transaction> transactions);


}
