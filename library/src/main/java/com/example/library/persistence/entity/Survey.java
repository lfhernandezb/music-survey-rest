package com.example.library.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
// @Data
@Table(name = "survey")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_survey", nullable = false)
    private long surveyId;
    @Column(nullable = false, unique = true, length = 128)
    private String email;

    //@JsonIgnoreProperties("musicStyle")
    //@ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "id_music_style") //, insertable = false, updatable = false)
    @Column(name = "id_music_style", nullable = false)
    private int musicStyleId;

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMusicStyleId() {
        return musicStyleId;
    }

    public void setMusicStyleId(int musicStyleId) {
        this.musicStyleId = musicStyleId;
    }
}