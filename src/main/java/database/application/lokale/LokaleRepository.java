package database.application.lokale;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface LokaleRepository extends CrudRepository<Lokal, Integer> {
}
