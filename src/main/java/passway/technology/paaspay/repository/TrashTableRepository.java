package passway.technology.paaspay.repository;

import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.TrashTable;


@JaversSpringDataAuditable
public interface TrashTableRepository extends MongoRepository<TrashTable, String> {
	

}
