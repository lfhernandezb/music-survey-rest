package com.example.survey.repository;

import com.example.library.dto.SurveyDto;
import com.example.library.persistence.entity.Survey;

import java.util.List;
import java.util.Optional;

public interface SurveyDtoRepository {
    SurveyDto save(SurveyDto surveyDto);
    Optional<SurveyDto> findByCorreo(String correo);
    Optional<SurveyDto> findById(long idEncuesta);
    List<SurveyDto> findAll();
    void deleteById(long idEncuesta);
    // void deleteByCorreo(String correo);
}
