package com.shilko.ru.witcher.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * The type Category component used for keeping possible categories of components.
 */
@Entity
@Data
public class CategoryComponent {

    /**
     * Instantiates a new Category component.
     */
    public CategoryComponent() {}

    /**
     * Instantiates a new Category component.
     *
     * @param name        the name
     * @param information the information
     */
    public CategoryComponent(@NotNull String name, String information) {
        this.name = name;
        this.information = information;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Type(type = "text")
    private String information;

    @Column(nullable = false)
    @OneToMany(mappedBy = "categoryComponent", fetch = FetchType.LAZY)
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
        CategoryComponent that = (CategoryComponent) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(information, that.information) &&
                Objects.equals(components, that.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, information, components);
    }
}
