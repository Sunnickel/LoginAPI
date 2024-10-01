package at.sunnickel.loginapi.repository;

import org.springframework.data.repository.ListCrudRepository;

/**
 * The interface Friend repository.
 */
public interface FriendRepository extends ListCrudRepository<at.sunnickel.loginapi.model.Friend, Long> {}