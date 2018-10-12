package com.shilko.ru.wither.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class EffectThing {

    public EffectThing() {}

    public EffectThing(@NotNull String name, @NotNull String description, String information, @NotNull Thing thing) {
        this.name = name;
        this.information = information;
        this.thing = thing;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Type(type = "text")
    private String information;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thing_id", nullable = false)
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
