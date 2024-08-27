package com.example.score.web.controller;

import com.example.score.dto.ScoreDto;
import com.example.score.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/scores")
@RestController
public class ScoreController {
    private final Logger logger = LoggerFactory.getLogger(ScoreController.class);

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/all")
    public ResponseEntity<List<ScoreDto>> allMusicStyles() {
        logger.info("allMusicStyles invoked");
        List<ScoreDto> musicStyleDTOList = scoreService.getAll();

        return ResponseEntity.ok(musicStyleDTOList);
    }

}
