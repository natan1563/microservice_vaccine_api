package api.vacinacao.vacina.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaccine {
    @Id
    private String id;
    private String manufacturer;
    private String batch;
    private LocalDate validateDate;
    private int amountOfDose;
    private int intervalBetweenDoses;
}
