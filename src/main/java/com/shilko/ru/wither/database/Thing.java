package com.shilko.ru.wither.database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

//@Entity
public class Thing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "typething_id", nullable = false)
    private long typeThingId;

    @NotNull
    @PositiveOrZero
    private int price;

    @NotNull
    @PositiveOrZero
    private double weight;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "descriptionthing_id", nullable = false)
    private long descriptionThingId;
}
