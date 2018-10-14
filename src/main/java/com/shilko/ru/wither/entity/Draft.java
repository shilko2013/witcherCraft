package com.shilko.ru.wither.entity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Draft implements Serializable {

    public Draft() { }

    public Draft(@NotNull Thing thing, String information, @NotNull List<Component> components) {
        this.thing = thing;
        this.information = information;
        this.components = components;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thing_id", nullable = false)
    private Thing thing;

    @Type(type = "text")
    private String information;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "draft_component",
            joinColumns = { @JoinColumn(name = "draft_id")},
            inverseJoinColumns = { @JoinColumn(name = "component_id")}
    )
    private List<Component> components;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Draft draft = (Draft) o;
        return id == draft.id &&
                Objects.equals(thing, draft.thing) &&
                Objects.equals(information, draft.information) &&
                Objects.equals(components, draft.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, thing, information, components);
    }
}
