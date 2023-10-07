package api.vacinacao.vacina.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDTO {
    @NotEmpty
    private String manufacturer;

    @NotEmpty
    private String batch;

    @NotNull
    private LocalDate validateDate;

    @NotNull
    private int amountOfDose;

    @NotNull
    private int intervalBetweenDoses;
}
