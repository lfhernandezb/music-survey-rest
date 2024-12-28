package com.example.survey.repository;

import com.example.library.dto.SurveyDto;
import com.example.library.exception.SurveyNotFoundException;
import com.example.library.mapper.SurveyDtoMapper;
import com.example.library.persistence.entity.Survey;
import com.example.survey.persistence.crud.SurveyCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class SurveyDtoRepositoryImpl implements SurveyDtoRepository {

    private final Logger logger = LoggerFactory.getLogger(SurveyDtoRepositoryImpl.class);

    @Autowired
    private SurveyCrudRepository surveyCrudRepository;

    @Autowired
    private SurveyDtoMapper surveyDtoMapper;

    @Override
    public SurveyDto save(SurveyDto surveyDto) {
        AtomicReference<Survey> ret = null;

        logger.info("to persist SurveyDto: " + surveyDto.toString());

        //Survey survey = surveyCrudRepository.save(surveyDtoMapper.toSurvey(surveyDto));

        Survey survey = surveyDtoMapper.toSurvey(surveyDto);

        System.out.println("Survey: " + survey.toString());

        Survey p = surveyCrudRepository.save(survey);

        logger.info("persisted Survey: " + p.toString());

        return surveyDtoMapper.toSurveyDto(p);
    }

    @Override
    public Optional<SurveyDto> findByCorreo(String correo) {
        return Optional.ofNullable(surveyCrudRepository.findByEmail(correo)
                .map(survey ->
                        surveyDtoMapper.toSurveyDto(survey)).orElseThrow(() -> new SurveyNotFoundException("Encuesta no encontrada para correo " + correo)));

    }

    @Override
    public Optional<SurveyDto> findById(long idEncuesta) {
        return Optional.ofNullable(surveyCrudRepository.findById(idEncuesta)
                .map(survey ->
                        surveyDtoMapper.toSurveyDto(survey)).orElseThrow(() -> new SurveyNotFoundException("Encuesta no encontrada para id " + String.valueOf(idEncuesta))));

    }

    @Override
    public List<SurveyDto> findAll() {
        List<Survey> list = (List<Survey>) surveyCrudRepository.findAll();

        logger.info("surveys list in findAll(): " + list.toString());
        return surveyDtoMapper.toSurveyDtos(list);
    }

    @Override
    public void deleteById(long idEncuesta) {
        surveyCrudRepository.deleteById(idEncuesta);
    }

    /*
    @Override
    public void deleteByCorreo(String correo) {
        Optional<Survey> p = surveyCrudRepository.findByEmail(correo);

        p.map(survey -> surveyCrudRepository.delete(survey)).
                orElseThrow(NotFoundException::new);
    }
    */
}
