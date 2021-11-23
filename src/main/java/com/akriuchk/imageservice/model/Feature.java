package com.akriuchk.imageservice.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "feature")
@Data
public class Feature {

    @Id
    @Column(name = "id")
    private UUID id;

    private Long timestamp;
    private Long beginViewingDate;
    private Long endViewingDate;
    private String missionName;

}
