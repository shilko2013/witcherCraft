package com.shilko.ru.witcher.entity;

import lombok.Data;
import org.springframework.http.MediaType;

import javax.persistence.*;

@Entity
@Data
public class Image {

    public static MediaType getMediaType(Image image) {
        switch (image.type) {
            case "gif":
                return MediaType.IMAGE_GIF;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpeg":
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.ALL;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Lob
    @Column(nullable = false)
    private byte[] picture;

    @OneToOne(mappedBy = "descriptionComponent", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Component component;

    public Image() {
    }

    public Image(String type, byte[] picture, Component component) {
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
