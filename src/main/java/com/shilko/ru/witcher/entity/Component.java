package com.shilko.ru.witcher.entity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Objects;

/**
 * The type Component is a component for craft things.
 */
@Entity
@Data
public class Component {

    /**
     * Instantiates a new Component.
     */
    public Component() {}

    /**
     * Instantiates a new Component.
     *
     * @param name                 the name
     * @param price                the price
     * @param weight               the weight
     * @param descriptionComponent the description component
     * @param categoryComponent    the category component
     */
    public Component(@NotNull String name, @NotNull @PositiveOrZero int price, @NotNull @PositiveOrZero double weight, @NotNull DescriptionComponent descriptionComponent, @NotNull CategoryComponent categoryComponent, @NotNull CraftOrAlchemy isAlchemy) {
        this.name = name;
        this.categoryComponent = categoryComponent;
        this.price = price;
        this.weight = weight;
        this.descriptionComponent = descriptionComponent;
        this.isAlchemy = isAlchemy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "craftormagic_id", nullable = false)
    private CraftOrAlchemy isAlchemy;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryComponent categoryComponent;

    @Column(nullable = false)
    @PositiveOrZero
    private int price;

    @Column(nullable = false)
    @PositiveOrZero
    private double weight;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "descriptioncomponent_id", nullable = false)
    private DescriptionComponent descriptionComponent;

    @Column(nullable = false)
    @ManyToMany(mappedBy = "components", fetch = FetchType.LAZY)
    private List<Draft> drafts;

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
     * Gets category component.
     *
     * @return the category component
     */
    public CategoryComponent getCategoryComponent() {
        return categoryComponent;
    }

    /**
     * Sets category.
     *
     * @param categoryComponent the category component
     */
    public void setCategory(CategoryComponent categoryComponent) {
        this.categoryComponent = categoryComponent;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets description component.
     *
     * @return the description component
     */
    public DescriptionComponent getDescriptionComponent() {
        return descriptionComponent;
    }

    /**
     * Sets description component.
     *
     * @param descriptionComponent the description component
     */
    public void setDescriptionComponent(DescriptionComponent descriptionComponent) {
        this.descriptionComponent = descriptionComponent;
    }

    /**
     * Gets drafts.
     *
     * @return the drafts
     */
    public List<Draft> getDrafts() {
        return drafts;
    }

    /**
     * Sets drafts.
     *
     * @param drafts the drafts
     */
    public void setDrafts(List<Draft> drafts) {
        this.drafts = drafts;
    }

    public CraftOrAlchemy isAlchemy() {
        return isAlchemy;
    }

    public void setAlchemy(CraftOrAlchemy alchemy) {
        isAlchemy = alchemy;
    }

    public void setCategoryComponent(CategoryComponent categoryComponent) {
        this.categoryComponent = categoryComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return id == component.id &&
                price == component.price &&
                Double.compare(component.weight, weight) == 0 &&
                Objects.equals(name, component.name) &&
                Objects.equals(categoryComponent, component.categoryComponent) &&
                Objects.equals(descriptionComponent, component.descriptionComponent) &&
                Objects.equals(drafts, component.drafts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, categoryComponent, price, weight, descriptionComponent, drafts);
    }
}
