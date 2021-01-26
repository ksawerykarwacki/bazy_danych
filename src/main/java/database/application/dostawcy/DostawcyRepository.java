package database.application.dostawcy;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface DostawcyRepository extends CrudRepository<Dostawca, DostawcaKey> {
}
