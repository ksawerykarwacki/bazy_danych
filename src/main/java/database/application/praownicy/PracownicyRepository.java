package database.application.praownicy;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PracownicyRepository extends CrudRepository<Pracownik, Integer> {
}
