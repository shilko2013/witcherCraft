package com.shilko.ru.wither.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class TypeThing {

    public TypeThing() {}

    public TypeThing(@NotNull String name, String information, @NotNull List<Thing> things) {
        this.name = name;
        this.information = information;
        this.things = things;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    private String information;

    @Column(nullable = false)
    @OneToMany(mappedBy = "typeThing", fetch = FetchType.LAZY)
    private List<Thing> things;

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

    public List<Thing> getThings() {
        return things;
    }

    public void setThings(List<Thing> things) {
        this.things = things;
    }
}
