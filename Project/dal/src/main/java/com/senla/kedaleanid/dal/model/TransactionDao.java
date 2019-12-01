package com.senla.kedaleanid.dal.model;


import com.senla.kedaleanid.dal.AbstractDao;
import com.senla.kedaleanid.dalapi.model.ITransactionDao;
import com.senla.kedaleanid.model.transaction.Transaction;
import org.springframework.stereotype.Repository;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Repository
public class TransactionDao extends AbstractDao<Transaction, Integer> implements ITransactionDao {

}
