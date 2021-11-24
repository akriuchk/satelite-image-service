package com.akriuchk.imageservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "image")
@Data
public class Image {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = SEQUENCE, generator = "image_seq")
    private Long id;

    private UUID uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @Basic
    private byte[] content;
}
