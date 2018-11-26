package com.shilko.ru.witcher.entity;

import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.Type;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
public class Image { //TODO edit double code

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

    @Type(type = "text")
    @Column(nullable = false)
    private String picture;

    @OneToOne(mappedBy = "image", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Component component;

    @OneToOne(mappedBy = "image", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Thing thing;

    public Image() {
    }

    public Image(String type, String picture, Component component) {
        this.type = type;
        this.picture = picture;
        this.component = component;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
