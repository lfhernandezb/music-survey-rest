package com.example.music_survey.score;

import com.example.score.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

//@SpringBootTest
@ContextConfiguration(classes = { ScoreService.class })
class ScoreApplicationTests {

	@Test
	void contextLoads() {
	}

}
