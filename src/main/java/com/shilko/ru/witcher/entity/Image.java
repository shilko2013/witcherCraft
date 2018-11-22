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

    public Image() { }

    public Image(long id, String name, String type, byte[] picture) {
        this.id = id;
        this.type = type;
        this.picture = picture;
    }
}
