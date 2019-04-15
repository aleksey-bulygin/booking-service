package com.project.booking.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "time_lines")
@Embeddable
@Data
public class TimeLine implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    @Column(name = "time_from")
    @JsonProperty("time_from")
    private String timeFrom;

    @Column(name = "time_to")
    @JsonProperty("time_to")
    private String timeTo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    @Embedded
  //  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "room_id")
  //  @JsonIdentityReference(alwaysAsId = true)
    private Room room;

}
