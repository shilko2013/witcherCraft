package com.shilko.ru.wither.database;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@Type(type="text")

@Entity
@Data
public class Users implements Serializable {

    public Users(@NotNull String login, @NotNull @Size(min = minPasswordSize) String password, @NotNull @Email String email, @NotNull UserStatus userStatus) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.userStatus = userStatus;
    }

    public Users(){}

    @Transient
    private static final int minPasswordSize = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique=true)
    private String login;

    @NotNull
    @Size(min = minPasswordSize)
    private String password;

    @NotNull
    @Column(unique=true)
    @Email
    private String email;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userstatus_id", nullable = false)
    private UserStatus userStatus;

    public static int getMinPasswordSize() {
        return minPasswordSize;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    @Override
    public String toString() {
        return super.toString() +
                "id = " + getId() + "; " +
                "login = " + getLogin() + "; " +
                "password = " + getPassword() + "; " +
                "email = " + getEmail() + "; " +
                "userStatus = " + getUserStatus() + ";\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this == o)
            return true;
        if (getClass() != o.getClass())
            return false;
        Users users = (Users) o;
        return id == users.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
