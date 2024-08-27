package com.example.library.mapper;

import com.example.library.dto.MusicStyleDto;
import com.example.library.dto.SurveyDto;
import com.example.library.persistence.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MusicStyleDto.class})
public interface SurveyDtoMapper {
    // entity -> dto
    @Mappings({
            @Mapping(source = "surveyId", target = "idEncuesta"),
            @Mapping(source = "email", target = "correo"),
            @Mapping(source = "musicStyleId", target = "idEstiloMusical"),
    })
    SurveyDto toSurveyDto(Survey survey);
    List<SurveyDto> toSurveyDtos(List<Survey> surveyList);

    // dto -> entity
    @Mappings({
            @Mapping(source = "idEncuesta", target = "surveyId"),
            @Mapping(source = "correo", target = "email"),
            @Mapping(source = "idEstiloMusical", target = "musicStyleId"),
    })
    //@InheritInverseConfiguration
    //@Mapping(target = "token", ignore = true)
    Survey toSurvey(SurveyDto surveyDto);
    List<Survey> toSurveys(List<SurveyDto> surveyDtoList);

}
