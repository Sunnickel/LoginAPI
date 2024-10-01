package at.sunnickel.loginapi.repository;

import org.springframework.data.repository.ListCrudRepository;

/**
 * The interface User repository.
 */
public interface UserRepository extends ListCrudRepository<at.sunnickel.loginapi.model.User, Long> {}
