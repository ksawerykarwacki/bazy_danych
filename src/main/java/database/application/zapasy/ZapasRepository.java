package database.application.zapasy;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ZapasRepository extends CrudRepository<Zapas, ZapasKey> {
}
