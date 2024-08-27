package com.example.musicstyle.persistence.crud;

import com.example.library.persistence.entity.MusicStyle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;

public interface MusicStyleCrudRepository extends CrudRepository<MusicStyle, Integer> {
    Optional<MusicStyle> findByDescription(String description);
}
