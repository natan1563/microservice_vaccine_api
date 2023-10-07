package api.vacinacao.vacina.controller;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.repository.VaccineRepository;
import api.vacinacao.vacina.services.VaccineService;
import api.vacinacao.vacina.services.dto.VaccineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vacina")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping
    public ResponseEntity<List<Vaccine>> getAll() {
        return ResponseEntity.ok().body(vaccineService.getAllVaccines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaccine> getVaccineById(@PathVariable String id) {
        return vaccineService.getVaccineById(id).map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }

    @PostMapping
    public ResponseEntity<Vaccine> registerVaccine(@RequestBody @Valid VaccineDTO vaccineDTO) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .build()
                .toUri();
        return ResponseEntity.created(location).body(vaccineService.registerVaccine(vaccineDTO));
    }
}
