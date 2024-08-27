package com.example.musicstyle.web.controller;

import com.example.library.dto.MusicStyleDto;
import com.example.musicstyle.services.MusicStyleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/musicstyles")
@RestController
public class MusicStyleController {
    private final Logger logger = LoggerFactory.getLogger(MusicStyleController.class);

    @Autowired
    private MusicStyleService musicStyleService;

    @GetMapping("/all")
    public ResponseEntity<List<MusicStyleDto>> allMusicStyles() {
        logger.info("allMusicStyles invoked");
        List<MusicStyleDto> musicStyleDTOList = musicStyleService.getAll();

        return ResponseEntity.ok(musicStyleDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicStyleDto> getMusicStyle(@PathVariable("id") int musicStyleId) {
        return ResponseEntity.ok(musicStyleService.getById(musicStyleId));
    }

}
