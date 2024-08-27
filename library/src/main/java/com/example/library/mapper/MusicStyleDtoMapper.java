package com.example.library.mapper;

import com.example.library.dto.MusicStyleDto;
import com.example.library.persistence.entity.MusicStyle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MusicStyleDtoMapper {
    // entity -> dto
    @Mappings({
            @Mapping(source = "musicStyleId", target = "idEstiloMusical"),
            @Mapping(source = "description", target = "estilo"),
    })
    MusicStyleDto toMusicStyleDto(MusicStyle musicStyle);
    List<MusicStyleDto> toMusicStyleDtos(List<MusicStyle> musicStyleList);

    // dto -> entity
    @Mappings({
            @Mapping(source = "idEstiloMusical", target = "musicStyleId"),
            @Mapping(source = "estilo", target = "description"),
            // @Mapping(source = "authorities", ignore = true),
    })
    //@InheritInverseConfiguration
    //@Mapping(target = "token", ignore = true)
    MusicStyle toMusicStyle(MusicStyleDto musicStyleDto);
    List<MusicStyle> toMusicStyles(List<MusicStyleDto> musicStyleDtoList);
}
