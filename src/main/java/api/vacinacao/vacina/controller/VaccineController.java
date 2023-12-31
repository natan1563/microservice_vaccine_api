package api.vacinacao.vacina.controller;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.handler.exceptions.UnprocessableEntityException;
import api.vacinacao.vacina.handler.exceptions.RegisterBadRequestException;
import api.vacinacao.vacina.handler.exceptions.ResourceNotFoundException;
import api.vacinacao.vacina.services.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @PostMapping
    public ResponseEntity<Vaccine> registerVaccine(@RequestBody @Valid Vaccine vaccine) throws RegisterBadRequestException, UnprocessableEntityException {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .build()
                .toUri();
        return ResponseEntity.created(location).body(vaccineService.registerVaccine(vaccine));
    }

    @PostMapping("/mock-vaccines")
    public ResponseEntity<Void> mockVaccines() {
        vaccineService.mockVaccines();
        return ResponseEntity.created(null).build();
    }

    @GetMapping
    public ResponseEntity<List<Vaccine>> getAll() {
        return ResponseEntity.ok().body(vaccineService.getAllVaccines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaccine> getVaccineById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(vaccineService.getVaccineById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaccine> update(@RequestBody @Valid Vaccine newVaccine, @PathVariable String id) throws ResourceNotFoundException, UnprocessableEntityException, RegisterBadRequestException {
        return ResponseEntity.ok().body(vaccineService.update(newVaccine, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws ResourceNotFoundException {
        vaccineService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        vaccineService.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
