package com.example.survey.services;

import com.example.library.dto.MusicStyleDto;
import com.example.library.dto.SurveyDto;
import com.example.library.exception.EmailAlreadyRegisteredException;
import com.example.library.exception.SurveyNotFoundException;
import com.example.library.persistence.entity.Survey;
import com.example.survey.repository.SurveyDtoRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    String serviceUrl = "http://MUSICSTYLE-SERVICE";

    private final Logger logger = LoggerFactory.getLogger(SurveyService.class);

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    private SurveyDtoRepositoryImpl surveyDtoRepository;

    public List<SurveyDto> getAll() {
        return surveyDtoRepository.findAll();
    }

    public Optional<SurveyDto> getById(long id) {
        logger.info("getById called");
        return surveyDtoRepository.findById(id);
    }

    public Optional<SurveyDto> getByEmail(String correo) {
        System.out.println("getByEmail invoked");
        return surveyDtoRepository.findByCorreo(correo);
    }

    @Transactional
    public SurveyDto save(SurveyDto surveyDto) {

        // voy a buscar el estilo musical al microservicio MUSICSTYLE-SERVICE
        // si la llamada falla, arroja RestClientException

        MusicStyleDto musicStyleDto = restTemplate
                .exchange(serviceUrl + "/api/musicstyles/{id}"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<MusicStyleDto>() {
                        }, surveyDto.getIdEstiloMusical()).getBody();

        // encuesta nueva

        // valido que no exista una encuesta con el email recibido
        if (getByEmail(surveyDto.getCorreo()).isPresent()) {
            throw new EmailAlreadyRegisteredException("El correo " + surveyDto.getCorreo() + " ya dio su encuesta");
        }

        // surveyDto.setIdEncuesta();

        System.out.println("SurveyDTO: " + surveyDto.toString());

        return surveyDtoRepository.save(surveyDto);

    }

    @Transactional
    public SurveyDto update(Long id, SurveyDto surveyDto) {

        // voy a buscar el estilo musical al microservicio MUSICSTYLE-SERVICE
        // si la llamada falla, arroja RestClientException

        MusicStyleDto musicStyleDto = restTemplate
                .exchange(serviceUrl + "/api/musicstyles/{id}"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<MusicStyleDto>() {
                        }, surveyDto.getIdEstiloMusical()).getBody();

        // actualizacion
        SurveyDto surveyDto1 = getById(id).orElseThrow(() -> new SurveyNotFoundException("No se encontró encuesta con id " + String.valueOf(id)));


        surveyDto.setIdEstiloMusical(surveyDto1.getIdEstiloMusical());
        surveyDto.setCorreo(surveyDto1.getCorreo());

        System.out.println("SurveyDTO: " + surveyDto.toString());

        return surveyDtoRepository.save(surveyDto);

    }

    @Transactional
    public boolean delete(long id) {
        logger.info("delete called");
        //SurveyDto surveyDto = getById(id);
        surveyDtoRepository.deleteById(id);
        return true;
    }

}
