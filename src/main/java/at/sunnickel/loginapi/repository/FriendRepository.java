package at.sunnickel.loginapi.repository;

import at.sunnickel.loginapi.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

/**
 * The interface Friend repository.
 */
public interface FriendRepository extends ListCrudRepository<Friend, Long> {}