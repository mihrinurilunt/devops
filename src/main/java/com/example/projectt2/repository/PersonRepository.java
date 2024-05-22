package com.example.projectt2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectt2.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
