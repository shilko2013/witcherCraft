package com.shilko.ru.wither.entity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
public class UserStatus implements Serializable {
    public UserStatus(@NotNull String status) {
        this.status = status;
    }

    public UserStatus(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<Users> users;

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Users> getUsers() {
        return users;
    }

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
