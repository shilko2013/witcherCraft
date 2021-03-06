package com.shilko.ru.witcher.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The type Thing.
 */
@Entity
@Data
public class Thing implements Serializable {

    /**
     * Instantiates a new Thing.
     */
    public Thing() { }

    /**
     * Instantiates a new Thing.
     *
     * @param name             the name
     * @param price            the price
     * @param weight           the weight
     * @param typeThing        the type thing
     * @param descriptionThing the description thing
     */
    public Thing(@NotNull String name,
                 @NotNull @PositiveOrZero int price,
                 @NotNull @PositiveOrZero double weight,
                 @NotNull TypeThing typeThing,
                 @NotNull DescriptionThing descriptionThing,
                 @NotNull boolean isAlchemy,
                 Image image) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.descriptionThing = descriptionThing;
        this.typeThing = typeThing;
        this.isAlchemy = isAlchemy;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typething_id", nullable = false)
    private TypeThing typeThing;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private double weight;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "descriptionthing_id", nullable = false)
    private DescriptionThing descriptionThing;

    @Column(nullable = false)
    @OneToMany(mappedBy = "thing", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<EffectThing> effects;

    @Column(nullable = false)
    @OneToMany(mappedBy = "thing", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Draft> drafts;

    @Column(nullable = false)
    private boolean isAlchemy;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", nullable = true)
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isAlchemy() {
        return isAlchemy;
    }

    public void setAlchemy(boolean alchemy) {
        isAlchemy = alchemy;
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
     * Gets type thing.
     *
     * @return the type thing
     */
    public TypeThing getTypeThing() {
        return typeThing;
    }

    /**
     * Sets type thing.
     *
     * @param typeThing the type thing
     */
    public void setTypeThing(TypeThing typeThing) {
        this.typeThing = typeThing;
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
     * Gets description thing.
     *
     * @return the description thing
     */
    public DescriptionThing getDescriptionThing() {
        return descriptionThing;
    }

    /**
     * Sets description thing.
     *
     * @param descriptionThing the description thing
     */
    public void setDescriptionThing(DescriptionThing descriptionThing) {
        this.descriptionThing = descriptionThing;
    }

    /**
     * Gets effects.
     *
     * @return the effects
     */
    public List<EffectThing> getEffects() {
        return effects;
    }

    /**
     * Sets effects.
     *
     * @param effects the effects
     */
    public void setEffects(List<EffectThing> effects) {
        this.effects = effects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thing thing = (Thing) o;
        return id == thing.id &&
                price == thing.price &&
                Double.compare(thing.weight, weight) == 0 &&
                Objects.equals(name, thing.name) &&
                Objects.equals(typeThing, thing.typeThing) &&
                Objects.equals(descriptionThing, thing.descriptionThing) &&
                Objects.equals(effects, thing.effects) &&
                Objects.equals(drafts, thing.drafts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, typeThing, price, weight, descriptionThing, effects, drafts);
    }
}
