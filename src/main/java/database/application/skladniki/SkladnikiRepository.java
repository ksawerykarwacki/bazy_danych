package database.application.skladniki;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface SkladnikiRepository extends CrudRepository<Skladnik, Integer> {
}
