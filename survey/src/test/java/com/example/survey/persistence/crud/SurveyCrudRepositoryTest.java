package com.example.survey.persistence.crud;

import com.example.library.dto.MusicStyleDto;
import com.example.library.exception.EmailAlreadyRegisteredException;
import com.example.survey.repository.SurveyDtoRepositoryImpl;
import com.example.library.dto.SurveyDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
the @Transactional annotation to ensure that changes are rolled back after each test, even if they're not made in a repository method
@ContextConfiguration is an annotation that can be applied to a test class to configure metadata that is used to determine how to load and configure an ApplicationContext for integration tests. Specifically, @ContextConfiguration declares the application context resource locations or the component classes used to load the context.
By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test. They also use an embedded in-memory database (replacing any explicit or usually auto-configured DataSource). The @AutoConfigureTestDatabase annotation can be used to override these settings.
@AutoConfigureTestDatabase: Annotation that can be applied to a test class to configure a test database to use instead of the application-defined or auto-configured DataSource. In the case of multiple DataSource beans, only the @Primary DataSource is considered.
@AutoConfigureWebClient: Annotation that can be applied to a test class to enable and configure auto-configuration of web clients.
@EntityScan:  Using @EntityScan will cause auto-configuration to:

    Set the packages scanned for JPA entities.
    Set the initial entity set used with Spring Data MongoDB, Neo4j, Cassandra and Couchbase mapping contexts.

@Transactional: it means that every test method in your suite is surrounded by an overarching Spring transaction. This transaction will be rolled back at the end of the test method regardless of it's outcome.
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

    private SurveyDto surveyDto1;

    private SurveyDto surveyDto2;

    @Autowired
    private SurveyDtoRepositoryImpl surveyDtoRepository;

    @BeforeEach
    void setUp() {

        surveyDto1 = SurveyDto.builder()
                .correo("someone@somedomain.com")
                .idEstiloMusical(1)
                .build();

        surveyDto2 = SurveyDto.builder()
                .correo("someone@somedomain.com")
                .idEstiloMusical(2)
                .build();

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

        assertTrue(surveyDtoRepository.findAll().iterator().next().getIdEncuesta() != 0L);
    }

    @Test
    public void whenAddSurveyWithExistingEmail_thenError() {

        surveyDtoRepository.save(surveyDto1);

        assertTrue(surveyDtoRepository.findAll().iterator().hasNext());

        assertTrue(surveyDtoRepository.findAll().iterator().next().getCorreo().equals(surveyDto1.getCorreo()));

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            surveyDtoRepository.save(surveyDto1);
        });
        /*
        String expectedMessage = "El correo ya dio su encuesta";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        */
    }



}