package com.shilko.ru.wither.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class CategoryComponent {

    public CategoryComponent() {}

    public CategoryComponent(@NotNull String name, String information) {
        this.name = name;
        this.information = information;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Type(type = "text")
    private String information;

    @Column(nullable = false)
    @OneToMany(mappedBy = "categoryComponent", fetch = FetchType.LAZY)
    private List<Component> components;

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
        CategoryComponent that = (CategoryComponent) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(information, that.information) &&
                Objects.equals(components, that.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, information, components);
    }
}
