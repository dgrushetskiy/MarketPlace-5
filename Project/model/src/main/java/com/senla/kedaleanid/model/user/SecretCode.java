package com.senla.kedaleanid.model.user;

import com.senla.kedaleanid.model.IModel;
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

@Entity(name = "SecretCode")
@Table(name = "secret_code", schema = "advertisements")
@Convertible
public class SecretCode implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer userId;
    @Column(name = "code")
    private Integer code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    public SecretCode() {
    }

    public SecretCode(Integer userId, Integer code) {
        this.userId = userId;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecretCode)) return false;
        SecretCode that = (SecretCode) o;
        return getUserId().equals(that.getUserId()) &&
                getCode().equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getCode());
    }

    @Override
    public String toString() {
        return "SecretCode{" +
                "userId=" + userId +
                ", code='" + code + '\'' +
                '}';
    }
}

