package com.example.survey.repository;

import com.example.library.dto.SurveyDto;

import java.util.List;

public interface SurveyDtoRepository {
    SurveyDto save(SurveyDto surveyDto);
    SurveyDto findByCorreo(String correo);
    SurveyDto findById(long idEncuesta);
    List<SurveyDto> findAll();
    void deleteById(long idEncuesta);
    // void deleteByCorreo(String correo);
}
