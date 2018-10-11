package com.shilko.ru.wither.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class DescriptionComponent {

    public DescriptionComponent() {}

    public DescriptionComponent(@NotNull String description, @NotNull Component component) {
        this.description = description;
        this.component = component;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Type(type = "text")
    private String description;

    @NotNull
    @OneToOne(mappedBy = "descriptionComponent")
    private Component component;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
