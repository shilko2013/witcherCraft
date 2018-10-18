package com.shilko.ru.wither.entity;
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

/**
 * The type Users.
 */
@Entity
@Data
public class Users implements Serializable {

    /**
     * Instantiates a new Users.
     *
     * @param login      the login
     * @param password   the password
     * @param email      the email
     * @param userStatus the user status
     */
    public Users(@NotNull String login, @NotNull @Size(min = minPasswordSize) String password, @NotNull @Email String email, @NotNull UserStatus userStatus) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.userStatus = userStatus;
    }

    /**
     * Instantiates a new Users.
     */
    public Users(){}

    @Transient
    private static final int minPasswordSize = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true, nullable = false)
    private String login;

    @Column(nullable = false)
    @Size(min = minPasswordSize)
    private String password;

    @Column(unique=true, nullable = false)
    @Email
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userstatus_id", nullable = false)
    private UserStatus userStatus;

    /**
     * Gets min password size.
     *
     * @return the min password size
     */
    public static int getMinPasswordSize() {
        return minPasswordSize;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets user status.
     *
     * @return the user status
     */
    public UserStatus getUserStatus() {
        return userStatus;
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
