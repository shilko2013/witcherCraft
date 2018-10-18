package com.shilko.ru.wither.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The type Effect thing keeps some effects for thing.
 */
@Entity
@Data
public class EffectThing {

    /**
     * Instantiates a new Effect thing.
     */
    public EffectThing() {}

    /**
     * Instantiates a new Effect thing.
     *
     * @param name        the name
     * @param description the description
     * @param information the information
     * @param thing       the thing
     */
    public EffectThing(@NotNull String name, @NotNull String description, String information, @NotNull Thing thing) {
        this.name = name;
        this.information = information;
        this.thing = thing;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Type(type = "text")
    private String information;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thing_id", nullable = false)
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
        EffectThing that = (EffectThing) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(information, that.information) &&
                Objects.equals(thing, that.thing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, information, thing);
    }
}
