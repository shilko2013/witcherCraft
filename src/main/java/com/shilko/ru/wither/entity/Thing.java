package com.shilko.ru.wither.entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.List;

@Entity
public class Thing implements Serializable {

    public Thing() {}

    public Thing(@NotNull String name, @NotNull TypeThing typeThing, @NotNull @PositiveOrZero int price, @NotNull @PositiveOrZero double weight, @NotNull DescriptionThing descriptionThing, @NotNull List<Draft> drafts) {
        this.name = name;
        this.typeThing = typeThing;
        this.price = price;
        this.weight = weight;
        this.descriptionThing = descriptionThing;
        this.drafts = drafts;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "typething_id", nullable = false)
    private TypeThing typeThing;

    @NotNull
    @PositiveOrZero
    private int price;

    @NotNull
    @PositiveOrZero
    private double weight;

    @NotNull
    @OneToOne
    @JoinColumn(name = "descriptionthing_id", nullable = false)
    private DescriptionThing descriptionThing;

    @NotNull
    @OneToMany(mappedBy = "thing")
    private List<EffectThing> effects;

    @NotNull
    @OneToMany(mappedBy = "thing")
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
}
