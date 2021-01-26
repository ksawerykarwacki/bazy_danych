package database.application.dania;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface DaniaRepository extends CrudRepository<Danie, Integer> {
}
