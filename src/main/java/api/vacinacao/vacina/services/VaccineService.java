package api.vacinacao.vacina.services;

import api.vacinacao.vacina.entity.Vaccine;
import api.vacinacao.vacina.repository.VaccineRepository;
import api.vacinacao.vacina.services.dto.VaccineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
