package com.example.survey.services;

import com.example.library.dto.MusicStyleDto;
import com.example.library.dto.SurveyDto;
import com.example.survey.repository.SurveyDtoRepositoryImpl;
import jakarta.validation.constraints.AssertTrue;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

/*
the @Transactional annotation to ensure that changes are rolled back after each test, even if they're not made in a repository method
 */
@ContextConfiguration(classes = { SurveyDtoRepositoryImpl.class, SurveyService.class })
@DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @ExtendWith(SpringExtension.class)
// @ActiveProfiles({"dev", "test"})
// @EntityScan(basePackages = { "com.example.library" })
@ComponentScan("com.example")
@AutoConfigureWebClient
@EntityScan(basePackages = "com.example.library.persistence.entity")
@Transactional
@ExtendWith(MockitoExtension.class)
class SurveyServiceTest {

    String serviceUrl = "http://MUSICSTYLE-SERVICE";

    private MusicStyleDto musicStyleDto = MusicStyleDto.builder()
            .idEstiloMusical(1)
            .estilo("Rock")
            .build();

    private SurveyDto surveyDto1 = SurveyDto.builder()
            .correo("someone@somedomain.com")
            .idEstiloMusical(1)
            .build();

    private SurveyDto surveyDto2 = SurveyDto.builder()
            .correo("someone@somedomain.com")
            .idEstiloMusical(2)
            .build();

    @Mock
    private SurveyDtoRepositoryImpl surveyDtoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SurveyService surveyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(SurveyServiceTest.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSurveyIsAdded_thenControlFlowAsExpected() {

        ResponseEntity<MusicStyleDto> musicStyleDtoResponseEntity = new ResponseEntity<MusicStyleDto>(HttpStatus.ACCEPTED);

        Mockito.when(this.restTemplate.exchange(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.<HttpEntity<?>> any(),
                        ArgumentMatchers.<ParameterizedTypeReference<MusicStyleDto>> any(),
                                ArgumentMatchers.anyInt())
                        )
                .thenReturn(musicStyleDtoResponseEntity);

        Mockito.when(this.surveyDtoRepository.save(Mockito.any(SurveyDto.class)))
                .thenReturn(surveyDto1);

        SurveyDto s = this.surveyService.save(surveyDto1);

        Mockito.verify(this.surveyDtoRepository, Mockito.times(1))
                .save(surveyDto1);

        assertTrue(s.equals(surveyDto1));

    }
}