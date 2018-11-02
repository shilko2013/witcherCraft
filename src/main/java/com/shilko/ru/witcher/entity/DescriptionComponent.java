package com.shilko.ru.witcher.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The type Description component keeps description for each component of craft.
 */
@Entity
@Data
public class DescriptionComponent {

    /**
     * Instantiates a new Description component.
     */
    public DescriptionComponent() {}

    /**
     * Instantiates a new Description component.
     *
     * @param description the description
     */
    public DescriptionComponent(@NotNull String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Type(type = "text")
    private String description;

    @OneToOne(mappedBy = "descriptionComponent", fetch = FetchType.EAGER)
    private Component component;

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
     * Gets component.
     *
     * @return the component
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Sets component.
     *
     * @param component the component
     */
    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescriptionComponent that = (DescriptionComponent) o;
        return id == that.id &&
                Objects.equals(description, that.description) &&
                Objects.equals(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, component);
    }
}
