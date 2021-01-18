package database.application;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface ClicksRepository extends CrudRepository<Clicks, UUID> {
}
