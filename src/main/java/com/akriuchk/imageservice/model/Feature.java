package com.akriuchk.imageservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feature")
@Data
@Builder
public class Feature {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = SEQUENCE, generator = "feature_seq")
    private long id;

    private UUID uuid;
    private Long timestamp;
    private Long beginViewingDate;
    private Long endViewingDate;
    private String missionName;

    @OneToOne(mappedBy = "feature",
            fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Image image;

}
