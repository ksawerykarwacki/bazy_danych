package database.application.stanowiska;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface StanowiskaRepository extends CrudRepository<Stanowisko, Integer> {
}
