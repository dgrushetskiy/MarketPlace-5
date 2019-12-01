package com.senla.kedaleanid.model.user;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "UserCreds")
@Table(name = "user_creds", schema = "advertisements")
@Convertible
public class UserCreds implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    @ReferencedField(name = "id")
    private Integer userId;
    @Column(name = "user_login")
    @ReferencedField(name = "login")
    private String login;
    @Column(name = "user_password")
    @ReferencedField(name = "password")
    private String password;
    @OneToOne(mappedBy = "userCreds", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    public UserCreds() {
    }

    public UserCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserCreds{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCreds)) return false;
        UserCreds userCreds = (UserCreds) o;
        return userId.equals(userCreds.userId) &&
                login.equals(userCreds.login) &&
                password.equals(userCreds.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password);
    }
}
