package com.shilko.ru.wither.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Data
public class DescriptionComponent {

    public DescriptionComponent() {}

    public DescriptionComponent(@NotNull String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Type(type = "text")
    private String description;

    @OneToOne(mappedBy = "descriptionComponent", fetch = FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescriptionComponent that = (DescriptionComponent) o;
        return id == that.id &&
                Objects.equals(description, that.description) &&
                Objects.equals(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, component);
    }
}
