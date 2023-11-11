package api.vacinacao.vacina.services;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.exception.RegisterBadRequestException;
import api.vacinacao.vacina.exception.ResourceNotFoundException;
import api.vacinacao.vacina.repository.VaccineRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    public Vaccine registerVaccine(Vaccine vaccine) throws RegisterBadRequestException {
        if (vaccine.getAmountOfDose() > 1 && vaccine.getIntervalBetweenDoses() == null) {
            throw new RegisterBadRequestException();
        }
        return vaccineRepository.insert(vaccine);
    }

    public void mockVaccines() {
        List<Vaccine> mockVaccines = Arrays.asList(
                new Vaccine("Pfizer", "PF12345", LocalDate.of(2023, 12, 31), 2, 21),
                new Vaccine("Moderna", "MD67890", LocalDate.of(2023, 11, 30), 2, 28),
                new Vaccine("AstraZeneca", "AZ45678", LocalDate.of(2023, 10, 31), 2, 12),
                new Vaccine("Generic Pharma", "FLU2023", LocalDate.of(2023, 11, 15), 1, 0),
                new Vaccine("Johnson & Johnson", "JJ78901", LocalDate.of(2023, 9, 30), 1, 0)
        );
        mockVaccines.forEach(vaccine -> vaccineRepository.insert(vaccine));
    }

    @Transactional(readOnly = true)
    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vaccine getVaccineById(String id) throws ResourceNotFoundException {
        return findById(id);
    }

    public Vaccine update(Vaccine newVaccine ,String id) throws ResourceNotFoundException {
        Vaccine vaccine = findById(id);
        vaccine.setBatch(newVaccine.getBatch());
//        vaccine.setValidateDate(newVaccine.getValidateDate());
        vaccine.setAmountOfDose(newVaccine.getAmountOfDose());
        vaccine.setManufacturer(newVaccine.getManufacturer());
        vaccine.setIntervalBetweenDoses(newVaccine.getIntervalBetweenDoses());

//        BeanUtils.copyProperties(newVaccine, vaccineOptional.get());
        return vaccineRepository.save(vaccine);
    }

    public void delete(String id) throws ResourceNotFoundException {
        findById(id);
        vaccineRepository.deleteById(id);
    }

    private Vaccine findById(String id) throws ResourceNotFoundException {
        Optional<Vaccine> vaccine = vaccineRepository.findById(id);

        if (vaccine.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return vaccine.get();
    }
}
