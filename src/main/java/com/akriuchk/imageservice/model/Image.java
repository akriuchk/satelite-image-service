package com.akriuchk.imageservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Data
public class Image {

    @Id
    @Column(name = "id")
    private String id;

    @Basic
    private byte[] content;
}
