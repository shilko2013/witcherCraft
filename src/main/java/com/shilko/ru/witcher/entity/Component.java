package com.shilko.ru.witcher.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    public Component() {
    }

    /**
     * Instantiates a new Component.
     *
     * @param name                 the name
     * @param price                the price
     * @param weight               the weight
     * @param descriptionComponent the description component
     * @param categoryComponent    the category component
     */
    public Component(@NotNull String name,
                     @NotNull @PositiveOrZero int price,
                     @NotNull @PositiveOrZero double weight,
                     @NotNull DescriptionComponent descriptionComponent,
                     @NotNull CategoryComponent categoryComponent,
                     @NotNull boolean isAlchemy,
                     Image image) {
        this.name = name;
        this.categoryComponent = categoryComponent;
        this.price = price;
        this.weight = weight;
        this.descriptionComponent = descriptionComponent;
        this.isAlchemy = isAlchemy;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private boolean isAlchemy;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryComponent categoryComponent;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private double weight;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "descriptioncomponent_id", nullable = false)
    private DescriptionComponent descriptionComponent;

    @Column(nullable = false)
    @ManyToMany(mappedBy = "components", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Draft> drafts;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", nullable = true)
    private Image image;

    public boolean getAlchemy() {
        return isAlchemy;
    }

    public void setAlchemy(boolean isAlchemy) {
        this.isAlchemy = isAlchemy;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

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
