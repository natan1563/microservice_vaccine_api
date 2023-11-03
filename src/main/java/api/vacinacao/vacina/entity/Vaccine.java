package api.vacinacao.vacina.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaccine {

    @Id
    private String id;

    @NotEmpty(message = "O fabricante da vacina não foi informado!")
    private String manufacturer;

    @NotEmpty(message = "O lote da vacina não foi informado!")
    private String batch;

    @NotNull(message = "A data de validade da vacina não foi informada!")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate validateDate;

    @NotNull(message = "O número de doses da vacina não foi informado!")
    @Min(value = 1, message = "O número de doses da vacina deve ser maior que 0!")
    private Integer amountOfDose;

    @NotNull(message = "O intervalo mínimo entre doses em dias da vacina não foi informado!")
    @Min(value = 1, message = "O intervalo mínimo entre doses em dias deve ser maior que 0!")
    private Integer intervalBetweenDoses;

    public Vaccine(String manufacturer, String batch, LocalDate validateDate, int amountOfDose, int intervalBetweenDoses) {
        this.manufacturer = manufacturer;
        this.batch = batch;
        this.validateDate = validateDate;
        this.amountOfDose = amountOfDose;
        this.intervalBetweenDoses = intervalBetweenDoses;
    }
}
