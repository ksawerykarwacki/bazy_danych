package database.application;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
public class ClicksService {

    @Inject
    ClicksRepository repository;

    @Transactional
    public void save(Clicks dto) {
        repository.save(dto);
    }
}
