package com.example.musicstyle.services;

import com.example.library.dto.MusicStyleDto;
import com.example.library.exception.MusicStyleAlreadyExistsException;
import com.example.library.exception.MusicStyleNotFoundException;
import com.example.musicstyle.repository.MusicStyleDtoRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MusicStyleService {

    private final Logger logger = LoggerFactory.getLogger(MusicStyleService.class);

    @Autowired
    private MusicStyleDtoRepositoryImpl musicStyleDtoRepository;

    public List<MusicStyleDto> getAll() {
        return musicStyleDtoRepository.findAll();
    }

    public MusicStyleDto getById(int id) {
        logger.info("getById called");

        MusicStyleDto musicStyleDto = musicStyleDtoRepository.findById(id);

        if (musicStyleDto == null) {
            throw new MusicStyleNotFoundException("No se encontro estilo musical con id " + String.valueOf(id));
        }

        return musicStyleDto;
    }

    public MusicStyleDto getByDescription(String description) {
        logger.info("getByDescription called");
        return musicStyleDtoRepository.findByDescription(description);
    }

    @Transactional
    public MusicStyleDto save(MusicStyleDto musicStyleDto) {

        if (musicStyleDto.getIdEstiloMusical() == 0L) {
            // estilo nuevo

            // valido que no exista un estilo con la descripcion recibida
            if (musicStyleDtoRepository.findByDescription(musicStyleDto.getEstilo()) != null) {
                throw new MusicStyleAlreadyExistsException("El estilo ya existe");
            }

            // musicStyleDto.setIdEncuesta();

        } else {
            // actualizacion
            // verificamos que el estilo exista buscando por descripcion
            MusicStyleDto musicStyleDto1 = getByDescription(musicStyleDto.getEstilo());

            if (musicStyleDto1 != null) {
                musicStyleDto.setEstilo(musicStyleDto1.getEstilo());
            } else {
                throw new MusicStyleNotFoundException("Estilo no encontrado para actualizar");
            }
        }

        System.out.println("MusicStyleDTO: " + musicStyleDto.toString());

        MusicStyleDto ret = musicStyleDtoRepository.save(musicStyleDto);

        return ret;
    }

    @Transactional
    public boolean delete(int id) {
        logger.info("delete called");
        //MusicStyleDto musicStyleDto = getById(id);
        musicStyleDtoRepository.deleteById(id);
        return true;
    }

}
