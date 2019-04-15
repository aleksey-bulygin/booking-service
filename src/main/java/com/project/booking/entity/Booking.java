package com.project.booking.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Data
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" )
    @JsonProperty("id")
    private Long id;

    @JsonProperty("booking_date")
    @Column(name = "booking_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date booking_date;

    @JsonProperty("topic")
    @Length(max = 255)
    @Column(name = "topic")
    private String topic;

    @JsonProperty("employeer")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "employeer_id")
    @Embedded
    private Employeer employeer;

    @JsonProperty("timeLine")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "time_line_id")
    @Embedded
    private TimeLine timeLine;
}
