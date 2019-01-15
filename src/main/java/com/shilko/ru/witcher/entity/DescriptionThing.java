package com.shilko.ru.witcher.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The type Description thing keeps description for each thing.
 */
@Entity
@Data
public class DescriptionThing {

    /**
     * Instantiates a new Description thing.
     */
    public DescriptionThing() {}

    /**
     * Instantiates a new Description thing.
     *
     * @param description the description
     */
    public DescriptionThing(@NotNull String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Type(type = "text")
    private String description;

    @OneToOne(mappedBy = "descriptionThing", fetch = FetchType.EAGER)
    private Thing thing;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
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
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets thing.
     *
     * @return the thing
     */
    public Thing getThing() {
        return thing;
    }

    /**
     * Sets thing.
     *
     * @param thing the thing
     */
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

    @Override
    public String toString() {
        return "DescriptionThing{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
