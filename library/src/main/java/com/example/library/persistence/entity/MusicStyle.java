package com.example.library.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
//@Data
@Table(name = "music_style")
public class MusicStyle {

    @Id
    @Column(name = "id_music_style", nullable = false)
    private int musicStyleId;
    @Column(nullable = false, unique = true, length = 128)
    private String description;

    public int getMusicStyleId() {
        return musicStyleId;
    }

    public void setMusicStyleId(int musicStyleId) {
        this.musicStyleId = musicStyleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}