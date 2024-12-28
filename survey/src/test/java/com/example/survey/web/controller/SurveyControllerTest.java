package com.example.survey.web.controller;

import com.example.library.dto.MusicStyleDto;
import com.example.library.dto.SurveyDto;
import com.example.library.mapper.SurveyDtoMapper;
import com.example.survey.persistence.crud.SurveyCrudRepository;
import com.example.survey.repository.SurveyDtoRepositoryImpl;
import com.example.survey.services.SurveyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@ContextConfiguration(classes = { SurveyDtoRepositoryImpl.class, SurveyService.class, SurveyController.class })
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = SurveyController.class)
//@Transactional("hibernateTransactionManager")
//@TestExecutionListeners({})
class SurveyControllerTest {

    private MusicStyleDto musicStyleDto;

    private SurveyDto surveyDto1;

    private SurveyDto surveyDto2;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SurveyService surveyService;

    @MockBean
    private SurveyCrudRepository surveyCrudRepository;

    @MockBean
    protected RestTemplate restTemplate;

    @MockBean
    private SurveyDtoMapper surveyDtoMapper;

    @BeforeEach
    void setUp() {

        musicStyleDto = MusicStyleDto.builder()
                .idEstiloMusical(1)
                .estilo("Rock")
                .build();

        surveyDto1 = SurveyDto.builder()
                .correo("someone1@somedomain.com")
                .idEstiloMusical(1)
                .build();

        surveyDto2 = SurveyDto.builder()
                .correo("someone2@somedomain.com")
                .idEstiloMusical(2)
                .build();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenGetAllSurveys_thenFlowAsExpected() throws Exception {

        List<SurveyDto> list = new ArrayList<>();

        list.add(surveyDto1);
        list.add(surveyDto2);

        Mockito.when(surveyService.getAll())
                .thenReturn(list);

        // given(surveyService.save(Mockito.any())).willReturn(surveyDto1);

        mvc.perform(get("/surveys/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].correo", is(surveyDto1.getCorreo())))
                .andExpect(jsonPath("$[1].correo", is(surveyDto2.getCorreo())));
        verify(surveyService, VerificationModeFactory.times(1)).getAll();
        reset(surveyService);
    }

    @Test
    void whenGetSurveyById_thenReturnSurvey() throws Exception {
        Mockito.when(surveyService.getById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(surveyDto2));

        // given(surveyService.save(Mockito.any())).willReturn(surveyDto1);

        mvc.perform(MockMvcRequestBuilders.get("/surveys/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.correo", is(surveyDto2.getCorreo())));
        verify(surveyService, VerificationModeFactory.times(1)).getById(Mockito.anyLong());
        reset(surveyService);
    }

    @Test
    void whenGetSurveyByEmail_thenReturnSurvey() throws Exception {
        Mockito.when(surveyService.getByEmail(Mockito.any()))
                .thenReturn(Optional.ofNullable(surveyDto1));

        // given(surveyService.save(Mockito.any())).willReturn(surveyDto1);

        mvc.perform(MockMvcRequestBuilders.get("/surveys/param")
                .param("email", "someone@somedomain.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.correo", is(surveyDto1.getCorreo())));
        verify(surveyService, VerificationModeFactory.times(1)).getByEmail(Mockito.any());
        reset(surveyService);
    }

    @Test
    //@Transactional
    void whenPostSurvey_thenCreateSurvey() throws Exception {

        Mockito.when(surveyService.save(Mockito.any()))
                .thenReturn(surveyDto1);

        // given(surveyService.save(Mockito.any())).willReturn(surveyDto1);

        mvc.perform(MockMvcRequestBuilders.post("/surveys/save").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(surveyDto1))).andExpect(status().isCreated()).andExpect(jsonPath("$.correo", is(surveyDto1.getCorreo())));
        verify(surveyService, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(surveyService);
    }

    @Test
    void whenPutSurvey_thenCreateSurvey() throws Exception {

        // la encuesta que va a ser actualizada; es buscada por el ID
        Mockito.when(surveyService.getById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(surveyDto1));

        // la encuesta retornada por el método update
        Mockito.when(surveyService.update(Mockito.anyLong(), Mockito.any()))
                .thenReturn(surveyDto2);

        // given(surveyService.save(Mockito.any())).willReturn(surveyDto1);

        mvc.perform(MockMvcRequestBuilders.put("/surveys/save/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(surveyDto2))).andExpect(status().isAccepted()).andExpect(jsonPath("$.correo", is(surveyDto2.getCorreo())));
        verify(surveyService, VerificationModeFactory.times(1)).update(Mockito.anyLong(), Mockito.any());
        reset(surveyService);
    }

    @Test
    void delete() {
    }
}