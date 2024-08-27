package com.example.library.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicStyleDto {
    private int idEstiloMusical;
    private String estilo;

    public @NotEmpty(message = "Se requiere el idEstiloMusical.") int getIdEstiloMusical() {
        return idEstiloMusical;
    }

    public void setIdEstiloMusical(int idEstiloMusical) {
        this.idEstiloMusical = idEstiloMusical;
    }

    public @NotEmpty(message = "Se requiere la descripcion del estilo.") @Size(min = 4, max = 16) String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    @Override
    public String toString() {
        return "MusicStyleDto{" +
                "idEstiloMusical=" + idEstiloMusical +
                ", estilo='" + estilo + '\'' +
                '}';
    }
}
