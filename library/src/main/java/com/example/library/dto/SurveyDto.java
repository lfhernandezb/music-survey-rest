package com.example.library.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyDto {
    private long idEncuesta;
    private String correo;
    private int idEstiloMusical;

    public @NotEmpty(message = "Se requiere el correo.") @Email(message = "El email no es valido.",
            flags = {Pattern.Flag.CASE_INSENSITIVE}) String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public long getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(long idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public @NotNull(message = "Se requiere id del estilo musical.") @Min(value = 1, message = "El valor no puede ser menor a 1") int getIdEstiloMusical() {
        return idEstiloMusical;
    }

    public void setIdEstiloMusical(int idEstiloMusical) {
        this.idEstiloMusical = idEstiloMusical;
    }

    @Override
    public String toString() {
        return "SurveyDto{" +
                "idEncuesta=" + idEncuesta +
                ", correo='" + correo + '\'' +
                ", idEstiloMusical=" + idEstiloMusical +
                '}';
    }
}
