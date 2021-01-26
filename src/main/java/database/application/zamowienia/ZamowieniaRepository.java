package database.application.zamowienia;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ZamowieniaRepository extends CrudRepository<Zamowienie, Integer> {
}
