package com.shilko.ru.wither.entity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * The type User status - statuses of users.
 */
@Entity
@Data
public class UserStatus implements Serializable {
    /**
     * Instantiates a new User status.
     *
     * @param status the status
     */
    public UserStatus(@NotNull String status) {
        this.status = status;
    }

    /**
     * Instantiates a new User status.
     */
    public UserStatus(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<Users> users;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public List<Users> getUsers() {
        return users;
    }

    /**
     * Sets users.
     *
     * @param users the users
     */
    public void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatus that = (UserStatus) o;
        return id == that.id &&
                Objects.equals(status, that.status) &&
                Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, users);
    }
}
