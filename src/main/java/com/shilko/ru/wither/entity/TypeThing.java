package com.shilko.ru.wither.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TypeThing {

    public TypeThing() {}

    public TypeThing(@NotNull String name, @NotNull String information, @NotNull Thing thing) {
        this.name = name;
        this.information = information;
        this.thing = thing;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String information;

    @NotNull
    @OneToMany(mappedBy = "typeThing")
    private Thing thing;

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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }
}
