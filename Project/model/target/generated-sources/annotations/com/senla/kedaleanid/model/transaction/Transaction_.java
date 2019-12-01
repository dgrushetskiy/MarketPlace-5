package com.senla.kedaleanid.model.transaction;

import com.senla.kedaleanid.model.user.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transaction.class)
public abstract class Transaction_ {

	public static volatile SingularAttribute<Transaction, User> owner;
	public static volatile SingularAttribute<Transaction, Timestamp> date;
	public static volatile SingularAttribute<Transaction, Double> amount;
	public static volatile SingularAttribute<Transaction, Purpose> purpose;
	public static volatile SingularAttribute<Transaction, Integer> id;

	public static final String OWNER = "owner";
	public static final String DATE = "date";
	public static final String AMOUNT = "amount";
	public static final String PURPOSE = "purpose";
	public static final String ID = "id";

}

