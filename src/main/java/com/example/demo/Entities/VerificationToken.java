package com.example.demo.Entities;

import javax.persistence.*;

import java.time.Instant;


@Entity
@Table(name = "token", schema = "cointrader")
public class VerificationToken {
    private long id;
    //    private long user_id;
    private String token;

    @Access(AccessType.PROPERTY)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    private Instant expiryDate;

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
    @Column(name = "expiry_date")
    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    @Basic
//    @Column(name = "user_id")
//    public Long getUserId() {
//        return user_id;
//    }
//
//    public void setUserId(Long userId) {
//        this.user_id = userId;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VerificationToken that = (VerificationToken) o;

        if (id != that.id) return false;
        if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
//        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
//        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

}
