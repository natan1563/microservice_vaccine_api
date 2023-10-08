package api.vacinacao.vacina.services;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.repository.VaccineRepository;
import api.vacinacao.vacina.services.dto.VaccineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    public Vaccine registerVaccine(VaccineDTO vaccineDTO) {
        return vaccineRepository.insert(Vaccine.builder()
                .manufacturer(vaccineDTO.getManufacturer())
                .batch(vaccineDTO.getBatch())
                .validateDate(vaccineDTO.getValidateDate())
                .amountOfDose(vaccineDTO.getAmountOfDose())
                .intervalBetweenDoses(vaccineDTO.getIntervalBetweenDoses())
                .build());
    }

    @Transactional(readOnly = true)
    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vaccine> getVaccineById(String id) {
        return vaccineRepository.findById(id);
    }

    public void mockVaccines() {
        List<VaccineDTO> mockVaccines = new ArrayList<>();
        mockVaccines.add(
                new VaccineDTO("Pfizer", "PF12345", LocalDate.of(2023, 12, 31), 2, 21)
        );
        mockVaccines.add(
                new VaccineDTO("Moderna", "MD67890", LocalDate.of(2023, 11, 30), 2, 28)
        );
        mockVaccines.add(
                new VaccineDTO("AstraZeneca", "AZ45678", LocalDate.of(2023, 10, 31), 2, 12)
        );
        mockVaccines.add(
                new VaccineDTO("Generic Pharma", "FLU2023", LocalDate.of(2023, 11, 15), 1, 0)
        );
        mockVaccines.add(
                new VaccineDTO("Johnson & Johnson", "JJ78901", LocalDate.of(2023, 9, 30), 1, 0)
        );
        mockVaccines.forEach(
                this::registerVaccine
        );
    }
}
