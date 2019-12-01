package com.senla.kedaleanid.model.transaction;

import com.senla.kedaleanid.utility.mapper.annotation.Convertible;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Entity(name = "Purpose")
@Table(name = "transaction_purpose", schema = "advertisements")
@Convertible
public class Purpose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purpose_id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "purpose_name")
    private String name;
    @OneToMany(mappedBy = "purpose", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
