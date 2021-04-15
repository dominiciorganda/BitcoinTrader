package com.example.demo.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "buytransaction", schema = "cointrader")
public class BuytransactionEntity {
    private long id;
    private Instant transactionDate;
    //    private long userId;
    private CoinTypes coin;
    private double amount;
    private double actualPrice;
    private double paidPrice;

    @Access(AccessType.PROPERTY)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    private User user;

    public void setUser(User user) {
        this.user = user;
    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "transaction_date")
    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

//    @Basic
//    @Column(name = "user_id")
//    public long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(long userId) {
//        this.userId = userId;
//    }

    @Basic
    @Column(name = "coin")
    @Enumerated(EnumType.STRING)
    public CoinTypes getCoin() {
        return coin;
    }

    public void setCoin(CoinTypes coin) {
        this.coin = coin;
    }

    @Basic
    @Column(name = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "actual_price")
    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    @Basic
    @Column(name = "paid_price")
    public double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(double paidPrice) {
        this.paidPrice = paidPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuytransactionEntity that = (BuytransactionEntity) o;

        if (id != that.id) return false;
//        if (userId != that.userId) return false;
        if (Double.compare(that.amount, amount) != 0) return false;
        if (Double.compare(that.actualPrice, actualPrice) != 0) return false;
        if (Double.compare(that.paidPrice, paidPrice) != 0) return false;
        if (transactionDate != null ? !transactionDate.equals(that.transactionDate) : that.transactionDate != null)
            return false;
        if (coin != null ? !coin.equals(that.coin) : that.coin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (transactionDate != null ? transactionDate.hashCode() : 0);
//        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (coin != null ? coin.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(actualPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(paidPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
