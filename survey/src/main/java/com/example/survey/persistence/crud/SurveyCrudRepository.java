package com.example.survey.persistence.crud;

import com.example.library.persistence.entity.Survey;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SurveyCrudRepository extends CrudRepository<Survey, Long> {
    public Optional<Survey> findByEmail(String email);
}
