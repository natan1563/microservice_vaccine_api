package api.vacinacao.vacina.services;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.exception.ResourceNotFoundException;
import api.vacinacao.vacina.repository.VaccineRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    public Vaccine registerVaccine(Vaccine vaccine) {
        return vaccineRepository.insert(Vaccine.builder()
                .manufacturer(vaccine.getManufacturer())
                .batch(vaccine.getBatch())
                .validateDate(vaccine.getValidateDate())
                .amountOfDose(vaccine.getAmountOfDose())
                .intervalBetweenDoses(vaccine.getIntervalBetweenDoses())
                .build());
    }

    public void mockVaccines() {
        List<Vaccine> mockVaccines = new ArrayList<>();
        mockVaccines.add(
                new Vaccine("Pfizer", "PF12345", LocalDate.of(2023, 12, 31), 2, 21)
        );
        mockVaccines.add(
                new Vaccine("Moderna", "MD67890", LocalDate.of(2023, 11, 30), 2, 28)
        );
        mockVaccines.add(
                new Vaccine("AstraZeneca", "AZ45678", LocalDate.of(2023, 10, 31), 2, 12)
        );
        mockVaccines.add(
                new Vaccine("Generic Pharma", "FLU2023", LocalDate.of(2023, 11, 15), 1, 0)
        );
        mockVaccines.add(
                new Vaccine("Johnson & Johnson", "JJ78901", LocalDate.of(2023, 9, 30), 1, 0)
        );
        mockVaccines.forEach(
                this::registerVaccine
        );
    }

    @Transactional(readOnly = true)
    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vaccine getVaccineById(String id) throws ResourceNotFoundException {
        Optional<Vaccine> vaccineOptional = findById(id);
        verifyIfIsEmpty(vaccineOptional);
        return vaccineOptional.get();
    }

    public Vaccine update(Vaccine newVaccine ,String id) throws ResourceNotFoundException {
        Optional<Vaccine> vaccineOptional = findById(id);
        verifyIfIsEmpty(vaccineOptional);
        BeanUtils.copyProperties(newVaccine, vaccineOptional.get());
        return vaccineRepository.save(vaccineOptional.get());
    }

    public void delete(String id) throws ResourceNotFoundException {
        Optional<Vaccine> vaccineOptional = findById(id);
        verifyIfIsEmpty(vaccineOptional);
        vaccineRepository.deleteById(id);
    }

    private Optional<Vaccine> findById(String id) {
        return vaccineRepository.findById(id);
    }

    private void verifyIfIsEmpty(Optional<Vaccine> vaccineOptional) throws ResourceNotFoundException {
        if (vaccineOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }
    }

}
