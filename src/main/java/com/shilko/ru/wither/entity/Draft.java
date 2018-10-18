package com.shilko.ru.wither.entity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Draft keeps drafts of things.
 */
@Entity
@Data
public class Draft implements Serializable {

    /**
     * Instantiates a new Draft.
     */
    public Draft() { }

    /**
     * Instantiates a new Draft.
     *
     * @param thing       the thing
     * @param information the information
     * @param components  the components
     */
    public Draft(@NotNull Thing thing, String information, @NotNull List<Component> components) {
        this.thing = thing;
        this.information = information;
        this.components = components;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thing_id", nullable = false)
    private Thing thing;

    @Type(type = "text")
    private String information;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "draft_component",
            joinColumns = { @JoinColumn(name = "draft_id")},
            inverseJoinColumns = { @JoinColumn(name = "component_id")}
    )
    private List<Component> components;

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
     * Gets components.
     *
     * @return the components
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * Sets components.
     *
     * @param components the components
     */
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Draft draft = (Draft) o;
        return id == draft.id &&
                Objects.equals(thing, draft.thing) &&
                Objects.equals(information, draft.information) &&
                Objects.equals(components, draft.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, thing, information, components);
    }
}
