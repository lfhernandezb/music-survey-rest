package com.example.music_survey.survey;

import com.example.survey.repository.SurveyDtoRepositoryImpl;
import com.example.survey.services.SurveyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


//@SpringBootTest
@ContextConfiguration(classes = { SurveyDtoRepositoryImpl.class, SurveyService.class })
class SurveyApplicationTests {

	@Test
	void contextLoads() {
	}

}
