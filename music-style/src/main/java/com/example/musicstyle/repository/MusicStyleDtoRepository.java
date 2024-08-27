package com.example.musicstyle.repository;

import com.example.library.dto.MusicStyleDto;

import java.util.List;

public interface MusicStyleDtoRepository {
    MusicStyleDto save(MusicStyleDto musicStyleDto);
    MusicStyleDto findById(int idEstiloMusical);
    MusicStyleDto findByDescription(String description);
    List<MusicStyleDto> findAll();
    void deleteById(int idEstiloMusical);
    // void deleteByCorreo(String correo);
}
