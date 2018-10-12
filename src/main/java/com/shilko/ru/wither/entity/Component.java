package com.shilko.ru.wither.entity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@Data
public class Component {

    public Component() {}

    public Component(@NotNull String name, @NotNull CategoryComponent categoryComponent, @NotNull @PositiveOrZero int price, @NotNull @PositiveOrZero double weight, @NotNull DescriptionComponent descriptionComponent, @NotNull List<Draft> drafts) {
        this.name = name;
        this.categoryComponent = categoryComponent;
        this.price = price;
        this.weight = weight;
        this.descriptionComponent = descriptionComponent;
        this.drafts = drafts;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryComponent categoryComponent;

    @Column(nullable = false)
    @PositiveOrZero
    private int price;

    @Column(nullable = false)
    @PositiveOrZero
    private double weight;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "descriptioncomponent_id", nullable = false)
    private DescriptionComponent descriptionComponent;

    @Column(nullable = false)
    @ManyToMany(mappedBy = "components", fetch = FetchType.LAZY)
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

    public CategoryComponent getCategoryComponent() {
        return categoryComponent;
    }

    public void setCategory(CategoryComponent categoryComponent) {
        this.categoryComponent = categoryComponent;
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

    public DescriptionComponent getDescriptionComponent() {
        return descriptionComponent;
    }

    public void setDescriptionComponent(DescriptionComponent descriptionComponent) {
        this.descriptionComponent = descriptionComponent;
    }

    public List<Draft> getDrafts() {
        return drafts;
    }

    public void setDrafts(List<Draft> drafts) {
        this.drafts = drafts;
    }
}
