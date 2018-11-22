package com.shilko.ru.witcher.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Lob
    @Column(nullable = false)
    private byte[] picture;

    @OneToOne(mappedBy = "descriptionComponent", fetch = FetchType.EAGER)
    private Component component;

    public Image() { }

    public Image(long id, String name, String type, byte[] picture, Component component) {
        this.id = id;
        this.type = type;
        this.picture = picture;
        this.component = component;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
