package com.example.survey.web.controller;

import com.example.library.dto.SurveyDto;
import com.example.survey.services.SurveyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/surveys")
@RestController
public class SurveyController {
    private final Logger logger = LoggerFactory.getLogger(SurveyController.class);

    @Autowired
    private SurveyService surveyService;

    @GetMapping("/all")
    public ResponseEntity<List<SurveyDto>> allSurveys() {
        logger.info("allSurveys invoked");
        List<SurveyDto> surveyDTOList = surveyService.getAll();

        return ResponseEntity.ok(surveyDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyDto> getSurvey(@PathVariable("id") int idEncuesta) {
        return ResponseEntity.ok(surveyService.getById(idEncuesta));
    }

    @GetMapping("/param")
    public ResponseEntity<SurveyDto> getSurveyByEmail(
            @RequestParam("email") String email) {
        SurveyDto surveyDto = surveyService.getByEmail(email);

        if (surveyDto != null) {
            return new ResponseEntity<>(surveyDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/save")
    public ResponseEntity<SurveyDto> create(@Valid @RequestBody SurveyDto survey) {
        return new ResponseEntity<>(surveyService.save(survey), HttpStatus.CREATED);
    }

    @PatchMapping("/save")
    public ResponseEntity<SurveyDto> update(@Valid @RequestBody SurveyDto survey) {
        return new ResponseEntity<>(surveyService.save(survey), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") long surveyId) {
        if (surveyService.delete(surveyId)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
