package com.project.booking.dto;

import com.project.booking.entity.Employeer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeerDto {

    private Long id;
    private String login;

    public static EmployeerDto from(Employeer employeer) {
        return new EmployeerDto().builder().
                id(employeer.getId()).
                login(employeer.getName()).
                build();
    }

    public static List<EmployeerDto> from(List<Employeer> employeers) {
        return employeers.stream().map(EmployeerDto::from).collect(Collectors.toList());
    }
}
