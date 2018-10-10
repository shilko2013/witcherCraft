package com.shilko.ru.wither.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @NotNull
    private String status;

    @NotNull
    @OneToMany(targetEntity=Users.class, fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    private Set<Users> users = new HashSet<>();

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return super.toString() +
                "id = " + getId() + "; " +
                "status = " + getStatus() + ";\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this == o)
            return true;
        if (getClass() != o.getClass())
            return false;
        UserStatus userStatus = (UserStatus) o;
        return id == userStatus.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
