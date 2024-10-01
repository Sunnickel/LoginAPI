package at.sunnickel.loginapi.repository;

import at.sunnickel.loginapi.model.Server;
import org.springframework.data.repository.ListCrudRepository;

public interface ServerRepository extends ListCrudRepository<Server, Long> {
}
