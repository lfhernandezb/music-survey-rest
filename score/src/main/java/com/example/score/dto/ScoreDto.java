package com.example.score.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScoreDto {
    String musicStyle;
    Float score;

    @Override
    public String toString() {
        return "ScoreDto{" +
                "musicStyle='" + musicStyle + '\'' +
                ", score=" + score +
                '}';
    }
}
