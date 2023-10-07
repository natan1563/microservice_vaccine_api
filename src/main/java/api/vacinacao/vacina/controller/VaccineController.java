package api.vacinacao.vacina.controller;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.repository.VaccineRepository;
import api.vacinacao.vacina.services.dto.VaccineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vacina")
public class VaccineController {
    @Autowired
    private VaccineRepository vaccineRepository;

    @GetMapping
    public ResponseEntity<List<Vaccine>> getAll() {
        return ResponseEntity.ok().body(vaccineRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Vaccine> registerVaccine(@RequestBody @Valid VaccineDto dtoVaccine) {
        Vaccine vaccineEntity = Vaccine
                .builder()
                .manufacturer(dtoVaccine.getManufacturer())
                .batch(dtoVaccine.getBatch())
                .validateDate(dtoVaccine.getValidateDate())
                .amountOfDose(dtoVaccine.getAmountOfDose())
                .intervalBetweenDoses(dtoVaccine.getIntervalBetweenDoses())
                .build();

        Vaccine returnedVaccine = vaccineRepository.insert(vaccineEntity);

        return ResponseEntity.created(null).body(returnedVaccine);
    }
}
