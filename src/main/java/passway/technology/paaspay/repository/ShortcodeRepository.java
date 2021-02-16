package passway.technology.paaspay.repository;


import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.ClientDetail;
import passway.technology.paaspay.model.Shortcode;
import passway.technology.paaspay.model.WaterconsumerDetail;


@JaversSpringDataAuditable
public interface ShortcodeRepository   extends MongoRepository<Shortcode, String> {
	
	  Optional<Shortcode> findByShortcodeAndClientdetail(String shortcode);
	
	  Boolean existsByShortcodeAndClientdetail(String shortcode,ClientDetail clientdetail);
	  List<Shortcode> findAllByClientdetail(ClientDetail clientdetail);

//	  @Query("select new package.DeadlineType(a.id, a.code) from ABDeadlineType a ")
//	  List<Shortcode> findAll();
}
