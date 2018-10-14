package com.shilko.ru.wither.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Thing implements Serializable {

    public Thing() { }

    public Thing(@NotNull String name, @NotNull @PositiveOrZero int price, @NotNull @PositiveOrZero double weight, @NotNull TypeThing typeThing, @NotNull DescriptionThing descriptionThing) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.descriptionThing = descriptionThing;
        this.typeThing = typeThing;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typething_id", nullable = false)
    private TypeThing typeThing;

    @Column(nullable = false)
    @PositiveOrZero
    private int price;

    @Column(nullable = false)
    @PositiveOrZero
    private double weight;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "descriptionthing_id", nullable = false)
    private DescriptionThing descriptionThing;

    @Column(nullable = false)
    @OneToMany(mappedBy = "thing", fetch = FetchType.LAZY)
    private List<EffectThing> effects;

    @Column(nullable = false)
    @OneToMany(mappedBy = "thing", fetch = FetchType.LAZY)
    private List<Draft> drafts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeThing getTypeThing() {
        return typeThing;
    }

    public void setTypeThing(TypeThing typeThing) {
        this.typeThing = typeThing;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public DescriptionThing getDescriptionThing() {
        return descriptionThing;
    }

    public void setDescriptionThing(DescriptionThing descriptionThing) {
        this.descriptionThing = descriptionThing;
    }

    public List<EffectThing> getEffects() {
        return effects;
    }

    public void setEffects(List<EffectThing> effects) {
        this.effects = effects;
    }

    public List<Draft> getDrafts() {
        return drafts;
    }

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
