package com.example.survey.dto;

import com.example.library.dto.MusicStyleDto;
import com.example.library.dto.SurveyDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class SurveyDtoTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void whenInvalidEmail_thenError() {

        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        // Creating object
        // jakarta.validation.Validator validator = factory.getValidator();

        MusicStyleDto musicStyleDto = new MusicStyleDto(1, "Rock");
        SurveyDto surveyDto = new SurveyDto(1, "a", 1);

        // It will validate each field
        Set<ConstraintViolation<SurveyDto>> violations = validator.validate(surveyDto);

        assertEquals((long) violations.size(), 1);
        assertEquals(violations.stream().toList().get(0).getMessage(), "El email no es valido.");

    }

    @Test
    void whenValidEmail_thenOk() {

        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        // Creating object
        // jakarta.validation.Validator validator = factory.getValidator();

        MusicStyleDto musicStyleDto = new MusicStyleDto(1, "Rock");
        SurveyDto surveyDto = new SurveyDto(1, "someone@example.com", musicStyleDto.getIdEstiloMusical());

        // It will validate each field
        Set<ConstraintViolation<SurveyDto>> violations = validator.validate(surveyDto);

        assertEquals(violations.stream().count(), 0);

    }

}