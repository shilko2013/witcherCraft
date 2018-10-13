package com.shilko.ru.wither.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Data
public class DescriptionThing {

    public DescriptionThing() {}

    public DescriptionThing(@NotNull String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Type(type = "text")
    private String description;

    @OneToOne(mappedBy = "descriptionThing", fetch = FetchType.LAZY)
    private Thing thing;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescriptionThing that = (DescriptionThing) o;
        return id == that.id &&
                Objects.equals(description, that.description) &&
                Objects.equals(thing, that.thing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, thing);
    }
}
