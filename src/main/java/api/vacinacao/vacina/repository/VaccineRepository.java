package api.vacinacao.vacina.repository;

import api.vacinacao.vacina.entity.Vaccine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends MongoRepository<Vaccine, String> {

}
