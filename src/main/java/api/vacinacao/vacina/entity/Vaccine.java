package api.vacinacao.vacina.entity;

import api.vacinacao.vacina.entity.util.DatabaseObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Vaccine extends DatabaseObject {

    @Id
    private String id;

    @NotEmpty(message = "O fabricante da vacina não foi informado!")
    private String manufacturer;

    @NotEmpty(message = "O lote da vacina não foi informado!")
    private String batch;

    @NotNull(message = "A data de validade da vacina não foi informada!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @FutureOrPresent
    private LocalDate validateDate;

    @NotNull(message = "O número de doses da vacina não foi informado!")
    @Min(value = 1, message = "O número de doses da vacina deve ser maior que 0!")
    private Integer amountOfDose;

    private Integer intervalBetweenDoses;

    public Vaccine(String manufacturer, String batch, LocalDate validateDate, int amountOfDose, int intervalBetweenDoses) {
        this.manufacturer = manufacturer;
        this.batch = batch;
        this.validateDate = validateDate;
        this.amountOfDose = amountOfDose;
        this.intervalBetweenDoses = intervalBetweenDoses;
    }
}
