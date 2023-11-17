package api.vacinacao.vacina.repository;

import api.vacinacao.vacina.entity.Vaccine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRepository extends MongoRepository<Vaccine, String> {
    List<Vaccine> findAllByOrderByCreatedAtDesc();
}
