package com.shilko.ru.witcher.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class CraftOrAlchemy {

    public CraftOrAlchemy() {}

    public CraftOrAlchemy(boolean isAlchemy) {
        this.isAlchemy = isAlchemy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private boolean isAlchemy;

    @Column(nullable = false)
    @OneToMany(mappedBy = "isAlchemy", fetch = FetchType.LAZY)
    private List<Thing> things;

    @Column(nullable = false)
    @OneToMany(mappedBy = "isAlchemy", fetch = FetchType.LAZY)
    private List<Draft> drafts;

    @Column(nullable = false)
    @OneToMany(mappedBy = "isAlchemy", fetch = FetchType.LAZY)
    private List<Component> components;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Thing> getThings() {
        return things;
    }

    public void setThings(List<Thing> things) {
        this.things = things;
    }

    public List<Draft> getDrafts() {
        return drafts;
    }

    public void setDrafts(List<Draft> drafts) {
        this.drafts = drafts;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public boolean isAlchemy() {
        return isAlchemy;
    }

    public void setAlchemy(boolean alchemy) {
        isAlchemy = alchemy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CraftOrAlchemy that = (CraftOrAlchemy) o;
        return id == that.id &&
                isAlchemy == that.isAlchemy &&
                Objects.equals(things, that.things) &&
                Objects.equals(drafts, that.drafts) &&
                Objects.equals(components, that.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isAlchemy, things, drafts, components);
    }
}
