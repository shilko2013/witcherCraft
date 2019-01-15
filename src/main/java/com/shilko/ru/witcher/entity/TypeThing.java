package com.shilko.ru.witcher.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * The type Type thing keeps types of things(like usual, magic, etc)
 */
@Entity
@Data
public class TypeThing {

    /**
     * Instantiates a new Type thing.
     */
    public TypeThing() {}

    /**
     * Instantiates a new Type thing.
     *
     * @param name        the name
     * @param information the information
     */
    public TypeThing(@NotNull String name, String information) {
        this.name = name;
        this.information = information;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    private String information;

    @Column(nullable = false)
    @OneToMany(mappedBy = "typeThing", fetch = FetchType.LAZY)
    private List<Thing> things;

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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets information.
     *
     * @return the information
     */
    public String getInformation() {
        return information;
    }

    /**
     * Sets information.
     *
     * @param information the information
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Gets things.
     *
     * @return the things
     */
    public List<Thing> getThings() {
        return things;
    }

    /**
     * Sets things.
     *
     * @param things the things
     */
    public void setThings(List<Thing> things) {
        this.things = things;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeThing typeThing = (TypeThing) o;
        return id == typeThing.id &&
                Objects.equals(name, typeThing.name) &&
                Objects.equals(information, typeThing.information) &&
                Objects.equals(things, typeThing.things);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, information, things);
    }

    @Override
    public String toString() {
        return "TypeThing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                '}';
    }
}
