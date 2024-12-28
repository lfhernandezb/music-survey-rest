package com.example.survey.web.controller;

import com.example.library.dto.SurveyDto;
import com.example.library.exception.SurveyNotFoundException;
import com.example.survey.services.SurveyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
        //return ResponseEntity.ok(surveyService.getById(idEncuesta));
        return surveyService.getById(idEncuesta)
                .map(surveyDto -> ResponseEntity.ok(surveyDto))
                .orElseThrow(() -> new SurveyNotFoundException("Encuesta no ecntrada para id " + String.valueOf(idEncuesta)));
    }

    @GetMapping("/param")
    public ResponseEntity<SurveyDto> getSurveyByEmail(
            @RequestParam("email") String email) {
        /*
        SurveyDto surveyDto = surveyService.getByEmail(email);

        if (surveyDto != null) {
            return new ResponseEntity<>(surveyDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
         */
        return surveyService.getByEmail(email)
                .map(surveyDto -> ResponseEntity.ok(surveyDto))
                .orElseThrow(() -> new SurveyNotFoundException("Encuesta no ecntrada para correo " + email));

    }

    @PostMapping("/save")
    public ResponseEntity<SurveyDto> create(@Valid @RequestBody SurveyDto survey) {
        return new ResponseEntity<>(surveyService.save(survey), HttpStatus.CREATED);
    }

    @PutMapping("/save/{id}")
    public ResponseEntity<SurveyDto> update(@PathVariable Long id, @Valid @RequestBody SurveyDto survey) {
        return new ResponseEntity<>(surveyService.update(id, survey), HttpStatus.ACCEPTED);
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
