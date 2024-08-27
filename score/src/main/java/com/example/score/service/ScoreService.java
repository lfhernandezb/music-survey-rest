package com.example.score.service;

import com.example.library.dto.MusicStyleDto;
import com.example.library.dto.SurveyDto;
import com.example.score.dto.ScoreDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    String surveyServiceUrl = "http://SURVEY-SERVICE";

    String musicStyleServiceUrl = "http://MUSICSTYLE-SERVICE";
    private final Logger logger = LoggerFactory.getLogger(ScoreService.class);

    @Autowired
    protected RestTemplate restTemplate;

    public List<ScoreDto> getAll() {

        int surveyNumber;
        List<ScoreDto> scoreDtoList = new ArrayList<>();

        // voy a SURVEY-SERVICE por las encuestas

        List<SurveyDto> surveyDtoList = restTemplate
                .exchange(surveyServiceUrl + "/api/surveys/all"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<List<SurveyDto>>() {
                        }).getBody();

        System.out.println(surveyDtoList);

        List<MusicStyleDto> musicStyleDtoList = restTemplate
                .exchange(musicStyleServiceUrl + "/api/musicstyles/all"
                        , HttpMethod.GET
                        , null
                        , new ParameterizedTypeReference<List<MusicStyleDto>>() {
                        }).getBody();

        System.out.println("musicStyleDtoList size: " + musicStyleDtoList.size());

        List<Integer> musicStyleIdList = surveyDtoList.stream().map(surveyDto -> surveyDto.getIdEstiloMusical()).collect(Collectors.toList());

        System.out.println("musicStyleIdList size: " + musicStyleIdList.size());

        surveyNumber = musicStyleIdList.size();

        System.out.println("surveyNumber: " + surveyNumber);

        if (surveyNumber > 0) {

            Map<Integer, Long> map = musicStyleIdList.stream().collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()));

            System.out.println(map);

            for (var entry : map.entrySet()) {

                MusicStyleDto msd = musicStyleDtoList.stream().filter(musicStyleDto -> musicStyleDto.getIdEstiloMusical() == entry.getKey()).collect(Collectors.toList()).get(0);

                System.out.println("msd: " + msd.toString());

                scoreDtoList.add(ScoreDto.builder()
                        .musicStyle(msd.getEstilo())
                        .score((float) (entry.getValue() * 100 / surveyNumber))
                        .build());

            }
        }

        System.out.println(scoreDtoList);

        return scoreDtoList;
    }

}
