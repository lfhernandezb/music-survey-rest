package com.example.score.service;

import com.example.library.dto.MusicStyleDto;
import com.example.library.dto.SurveyDto;
import com.example.score.dto.ScoreDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan("com.example")
@AutoConfigureWebClient
@EntityScan(basePackages = "com.example.library.persistence.entity")
@Transactional
@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ScoreService scoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(ScoreServiceTest.class);
    }

    @AfterEach
    void tearDown() {
    }
    
    @Test
    void whenSurveyIsAsked_thenControlFlowAsExpected() {

        String surveyServiceUrl = "http://SURVEY-SERVICE";

        String musicStyleServiceUrl = "http://MUSICSTYLE-SERVICE";

        List<SurveyDto> surveyDtoList = new ArrayList<>();

        surveyDtoList.add(SurveyDto.builder()
                .correo("a@domain.cl")
                .idEstiloMusical(1)
                .idEncuesta(1)
                .build());

        surveyDtoList.add(SurveyDto.builder()
                .correo("b@domain.cl")
                .idEstiloMusical(2)
                .idEncuesta(2)
                .build());
        surveyDtoList.add(SurveyDto.builder()
                .correo("c@domain.cl")
                .idEstiloMusical(3)
                .idEncuesta(3)
                .build());
        surveyDtoList.add(SurveyDto.builder()
                .correo("d@domain.cl")
                .idEstiloMusical(3)
                .idEncuesta(4)
                .build());
        surveyDtoList.add(SurveyDto.builder()
                .correo("e@domain.cl")
                .idEstiloMusical(4)
                .idEncuesta(5)
                .build());

        List<MusicStyleDto> musicStyleDtoList = new ArrayList<>();

        musicStyleDtoList.add(MusicStyleDto.builder()
                .estilo("Rock")
                .idEstiloMusical(1)
                .build());

        musicStyleDtoList.add(MusicStyleDto.builder()
                .estilo("Pop")
                .idEstiloMusical(2)
                .build());

        musicStyleDtoList.add(MusicStyleDto.builder()
                .estilo("Clásica")
                .idEstiloMusical(3)
                .build());

        musicStyleDtoList.add(MusicStyleDto.builder()
                .estilo("Salsa")
                .idEstiloMusical(4)
                .build());

        ResponseEntity<List<MusicStyleDto>> musicStyleDtoResponseEntity = new ResponseEntity<List<MusicStyleDto>>(musicStyleDtoList, HttpStatus.ACCEPTED);

        Mockito.when(this.restTemplate.exchange(
                        ArgumentMatchers.eq(musicStyleServiceUrl + "/api/musicstyles/all"),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.<HttpEntity<?>> any(),
                        ArgumentMatchers.<ParameterizedTypeReference<List<MusicStyleDto>>> any())
                )
                .thenReturn(musicStyleDtoResponseEntity);

        ResponseEntity<List<SurveyDto>> surveyDtoResponseEntity = new ResponseEntity<List<SurveyDto>>(surveyDtoList, HttpStatus.ACCEPTED);

        Mockito.when(this.restTemplate.exchange(
                        ArgumentMatchers.eq(surveyServiceUrl + "/api/surveys/all"),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.<HttpEntity<?>> any(),
                        ArgumentMatchers.<ParameterizedTypeReference<List<SurveyDto>>> any())
                )
                .thenReturn(surveyDtoResponseEntity);

        List<ScoreDto> scoreDtoList = scoreService.getAll();

        for (ScoreDto s: scoreDtoList) {
            System.out.println(s);
        }

        // System.out.println(scoreDtoList);

        assertTrue(scoreDtoList.size() == 4);

        assertTrue(scoreDtoList.stream().filter(scoreDto -> scoreDto.getMusicStyle().equals("Rock")).collect(Collectors.toList()).get(0).getScore() == 20.0);
    }

}