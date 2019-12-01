package com.senla.kedaleanid.model.transaction;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "Transaction")
@Table(name = "transaction", schema = "advertisements")
@Convertible
public class Transaction implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_owner_id")
    private User owner;
    @Column(name = "transaction_amount")
    private Double amount;
    @Column(name = "transaction_date")
    private java.sql.Timestamp date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_purpose")
    private Purpose purpose;

    public Transaction() {
    }

    public Transaction(double amount, User owner, java.sql.Timestamp date, Purpose purpose) {
        this.amount = amount;
        this.owner = owner;
        this.date = date;
        this.purpose = purpose;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public java.sql.Timestamp getDate() {
        return date;
    }

    public void setDate(java.sql.Timestamp date) {
        this.date = date;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", owner=" + owner +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", purpose='" + purpose + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return getId().equals(that.getId()) &&
                Double.compare(that.getAmount(), getAmount()) == 0 &&
                getOwner().equals(that.getOwner()) &&
                getDate().equals(that.getDate()) &&
                getPurpose().equals(that.getPurpose());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOwner(), getAmount(), getDate(), getPurpose());
    }
}
