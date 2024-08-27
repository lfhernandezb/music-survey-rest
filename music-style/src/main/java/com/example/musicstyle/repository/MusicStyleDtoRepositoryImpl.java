package com.example.musicstyle.repository;

import com.example.library.dto.MusicStyleDto;
import com.example.library.mapper.MusicStyleDtoMapper;
import com.example.musicstyle.persistence.crud.MusicStyleCrudRepository;
import com.example.library.persistence.entity.MusicStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MusicStyleDtoRepositoryImpl implements MusicStyleDtoRepository {

    private final Logger logger = LoggerFactory.getLogger(MusicStyleDtoRepositoryImpl.class);

    @Autowired
    private MusicStyleCrudRepository musicStyleCrudRepository;

    @Autowired
    private MusicStyleDtoMapper musicStyleDtoMapper;

    @Override
    public MusicStyleDto save(MusicStyleDto musicStyleDto) {

        logger.info("to persist MusicStyleDto: " + musicStyleDto.toString());

        //MusicStyle musicStyle = musicStyleCrudRepository.save(musicStyleDtoMapper.toMusicStyle(musicStyleDto));

        MusicStyle musicStyle = musicStyleDtoMapper.toMusicStyle(musicStyleDto);

        System.out.println("MusicStyle: " + musicStyle.toString());

        MusicStyle p = musicStyleCrudRepository.save(musicStyle);

        logger.info("persisted MusicStyle: " + p.toString());

        return musicStyleDtoMapper.toMusicStyleDto(p);
    }

    @Override
    public MusicStyleDto findById(int idEstiloMusical) {
        return musicStyleCrudRepository.findById(idEstiloMusical).map(musicStyle ->
                musicStyleDtoMapper.toMusicStyleDto(musicStyle)).orElse(null); //orElseThrow(() -> new MusicStyleNotFoundException("MusicStyle not found XXX"));
    }

    public MusicStyleDto findByDescription(String description) {
        return musicStyleCrudRepository.findByDescription(description).map(musicStyle ->
                musicStyleDtoMapper.toMusicStyleDto(musicStyle)).orElse(null); //orElseThrow(() -> new MusicStyleNotFoundException("MusicStyle not found XXX"));

    }

    @Override
    public List<MusicStyleDto> findAll() {
        List<MusicStyle> list = (List<MusicStyle>) musicStyleCrudRepository.findAll();

        logger.info("musicStyles list in findAll(): " + list.toString());
        return musicStyleDtoMapper.toMusicStyleDtos(list);
    }

    @Override
    public void deleteById(int idEstiloMusical) {
        musicStyleCrudRepository.deleteById(idEstiloMusical);
    }

    /*
    @Override
    public void deleteByCorreo(String correo) {
        Optional<MusicStyle> p = musicStyleCrudRepository.findByEmail(correo);

        p.map(musicStyle -> musicStyleCrudRepository.delete(musicStyle)).
                orElseThrow(NotFoundException::new);
    }
    */
}
