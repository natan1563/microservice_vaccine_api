package api.vacinacao.vacina.services;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.handler.exceptions.UnprocessableEntityException;
import api.vacinacao.vacina.handler.exceptions.RegisterBadRequestException;
import api.vacinacao.vacina.handler.exceptions.ResourceNotFoundException;
import api.vacinacao.vacina.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    public Vaccine registerVaccine(Vaccine vaccine) throws RegisterBadRequestException, UnprocessableEntityException {
        boolean intervalBetweenDosesIsEmpty = vaccine.getIntervalBetweenDoses() == null;
        if (vaccine.getAmountOfDose() > 1 && intervalBetweenDosesIsEmpty) {
            throw new RegisterBadRequestException("Para vacinas com quantidades de doses maiores que 1 (uma), deve ser informado o intervalo entre doses.");
        } else if (vaccine.getAmountOfDose() == 1 && !intervalBetweenDosesIsEmpty) {
            throw new RegisterBadRequestException("Para vacinas com quantidades de doses iguais a 1 (um), não poderá haver intervalo entre doses.");
        } else if (!intervalBetweenDosesIsEmpty && vaccine.getIntervalBetweenDoses() < 0) {
            throw new RegisterBadRequestException("O intervalo entre doses não pode ser menor que 0 (zero).");
        }

        Optional<Vaccine> vaccineHasBeenRegistered = vaccineRepository.findFirstByManufacturerIgnoreCase(vaccine.getManufacturer().trim());
        if (vaccineHasBeenRegistered.isPresent()) {
            throw new UnprocessableEntityException(
                    "A vacina do fornecedor " + vaccine.getManufacturer().trim() + " já foi cadastrada anteriormente."
            );
        }

        vaccine.setManufacturer(vaccine.getManufacturer().trim());
        vaccine.setBatch(vaccine.getBatch().trim());

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
        return vaccineRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Vaccine getVaccineById(String id) throws ResourceNotFoundException {
        return findById(id);
    }

    public Vaccine update(Vaccine newVaccine, String id) throws ResourceNotFoundException, UnprocessableEntityException, RegisterBadRequestException {
        boolean intervalBetweenDosesIsEmpty = newVaccine.getIntervalBetweenDoses() == null;
        if (newVaccine.getAmountOfDose() > 1 && intervalBetweenDosesIsEmpty) {
            throw new RegisterBadRequestException("Para vacinas com quantidades de doses maiores que 1 (uma), deve ser informado o intervalo entre doses.");
        } else if (newVaccine.getAmountOfDose() == 1 && !intervalBetweenDosesIsEmpty) {
            throw new RegisterBadRequestException("Para vacinas com quantidades de doses iguais a 1 (um), não poderá haver intervalo entre doses.");
        } else if (!intervalBetweenDosesIsEmpty && newVaccine.getIntervalBetweenDoses() < 0) {
            throw new RegisterBadRequestException("O intervalo entre doses não pode ser menor que 0 (zero).");
        }

        Vaccine vaccine = findById(id);
        Optional<Vaccine> vaccineHasBeenRegistered = vaccineRepository.findFirstByManufacturerIgnoreCase(newVaccine.getManufacturer().trim());

        if (vaccineHasBeenRegistered.isPresent() && !vaccine.getManufacturer().trim().equalsIgnoreCase(vaccineHasBeenRegistered.get().getManufacturer().trim())) {
            throw new UnprocessableEntityException(
                    "A vacina do fornecedor " + newVaccine.getManufacturer().trim() + " já foi cadastrada anteriormente."
            );
        }

        vaccine.setBatch(newVaccine.getBatch().trim());
        vaccine.setValidateDate(newVaccine.getValidateDate());
        vaccine.setAmountOfDose(newVaccine.getAmountOfDose());
        vaccine.setManufacturer(newVaccine.getManufacturer().trim());
        vaccine.setIntervalBetweenDoses(newVaccine.getIntervalBetweenDoses());
        return vaccineRepository.save(vaccine);
    }

    public void delete(String id) throws ResourceNotFoundException {
        findById(id);
        vaccineRepository.deleteById(id);
    }

    public void deleteAll() {
        vaccineRepository.deleteAll();
    }

    private Vaccine findById(String id) throws ResourceNotFoundException {
        Optional<Vaccine> vaccine = vaccineRepository.findById(id);

        if (vaccine.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return vaccine.get();
    }
}
