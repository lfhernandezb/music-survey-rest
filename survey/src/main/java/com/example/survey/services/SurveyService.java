package com.example.survey.services;

import com.example.library.dto.MusicStyleDto;
import com.example.library.dto.SurveyDto;
import com.example.library.exception.EmailAlreadyRegisteredException;
import com.example.library.exception.EmailNotFoundException;
import com.example.library.exception.MusicStyleNotFoundException;
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

    public SurveyDto getById(long id) {
        logger.info("getById called");
        return surveyDtoRepository.findById(id);
    }

    public SurveyDto getByEmail(String correo) {
        System.out.println("getByEmail invoked");
        return surveyDtoRepository.findByCorreo(correo);
    }

    @Transactional
    public SurveyDto save(SurveyDto surveyDto) {

        // voy a buscar el estilo musical al microservicio MUSICSTYLE-SERVICE

        MusicStyleDto musicStyleDto = restTemplate
                .exchange(serviceUrl + "/api/musicstyles/{id}"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<MusicStyleDto>() {
                        }, surveyDto.getIdEstiloMusical()).getBody();

        if (surveyDto.getIdEncuesta() == 0L) {
            // encuesta nueva

            // valido que no exista una encuesta con el email recibido
            if (getByEmail(surveyDto.getCorreo()) != null) {
                throw new EmailAlreadyRegisteredException("El correo ya dio su encuesta");
            }

            // surveyDto.setIdEncuesta();

        } else {
            // actualizacion
            // verificamos que el usuario exista buscando por email
            SurveyDto surveyDto1 = getByEmail(surveyDto.getCorreo());

            if (surveyDto1 != null) {
                surveyDto.setIdEstiloMusical(surveyDto1.getIdEstiloMusical());
            } else {
                throw new EmailNotFoundException("Correo no encontrado para actualizar: " + surveyDto.getCorreo());
            }
        }

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
