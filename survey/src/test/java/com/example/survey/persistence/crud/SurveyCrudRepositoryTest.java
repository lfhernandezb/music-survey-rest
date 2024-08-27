package com.example.survey.persistence.crud;

import com.example.library.exception.EmailAlreadyRegisteredException;
import com.example.survey.repository.SurveyDtoRepositoryImpl;
import com.example.library.dto.SurveyDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

/*
the @Transactional annotation to ensure that changes are rolled back after each test, even if they're not made in a repository method
 */
@ContextConfiguration(classes = { SurveyDtoRepositoryImpl.class })
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @ExtendWith(SpringExtension.class)
// @ActiveProfiles({"dev", "test"})
// @EntityScan(basePackages = { "com.example.library" })
@ComponentScan("com.example")
@AutoConfigureWebClient
@EntityScan(basePackages = "com.example.library.persistence.entity")
@Transactional
class SurveyCrudRepositoryTest {

    @Autowired
    private SurveyDtoRepositoryImpl surveyDtoRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void whenAddSurvey_thenOk() {
        SurveyDto surveyDto = SurveyDto.builder()
                .correo("someone@somedomain.com")
                .idEstiloMusical(1)
                .build();

        surveyDtoRepository.save(surveyDto);

        assertTrue(surveyDtoRepository.findAll().iterator().hasNext());

        assertTrue(surveyDtoRepository.findAll().iterator().next().getCorreo().equals("someone@somedomain.com"));
    }
    /*
    @Test
    public void whenAddSurveyWithExistingEmail_thenError() {
        SurveyDto surveyDto1 = SurveyDto.builder()
                .correo("someone@somedomain.com")
                .idEstiloMusical(1)
                .build();

        SurveyDto surveyDto2 = SurveyDto.builder()
                .correo("someone@somedomain.com")
                .idEstiloMusical(2)
                .build();

        surveyDtoRepository.save(surveyDto1);

        assertTrue(surveyDtoRepository.findAll().iterator().hasNext());

        assertTrue(surveyDtoRepository.findAll().iterator().next().getCorreo().equals("someone@somedomain.com"));

        Exception exception = assertThrows(EmailAlreadyRegisteredException.class, () -> {
            surveyDtoRepository.save(surveyDto2);
        });

        String expectedMessage = "El correo ya dio su encuesta";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
    */

}