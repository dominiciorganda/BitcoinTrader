package com.example.demo.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "funds", schema = "cointrader")
public class FundsEntity {
    private long id;
    private Instant transactionDate;
    //    private Long userId;
    private Double amount;
    private FundsType type;

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
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public FundsType getType() {
        return type;
    }

    public void setType(FundsType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FundsEntity that = (FundsEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(transactionDate, that.transactionDate))
            return false;
//        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (!Objects.equals(amount, that.amount)) return false;
        if (!Objects.equals(type, that.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (transactionDate != null ? transactionDate.hashCode() : 0);
//        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
