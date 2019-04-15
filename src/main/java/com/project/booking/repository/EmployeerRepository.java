package com.project.booking.repository;

import com.project.booking.entity.Employeer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeerRepository extends JpaRepository<Employeer, Long> {
    Optional<Employeer> findOneByName(String name);
}
