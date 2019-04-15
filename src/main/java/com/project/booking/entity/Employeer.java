package com.project.booking.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employeers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employeer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
  //  @SequenceGenerator( name = "employeers_seq", sequenceName = "employeers_seq", allocationSize = 1, initialValue = 100 )
   // @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "employeers_seq")
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

   // @OneToMany(fetch = FetchType.EAGER, mappedBy = "employeer", targetEntity = Token.class)
  //  @ElementCollection(targetClass = Token.class)
    @OneToMany(mappedBy = "employeer")//, targetEntity = Token.class,
  //  cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Token> tokens;
}
