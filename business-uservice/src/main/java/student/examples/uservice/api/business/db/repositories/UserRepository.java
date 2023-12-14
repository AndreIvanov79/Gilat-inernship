package student.examples.uservice.api.business.db.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import student.examples.uservice.api.business.db.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	@Query("SELECT u FROM User u WHERE u.token = :token")
    User findUserByToken(@Param("token") String token);
	
//	@Query("UPDATE User u SET u.isActive = true WHERE u.token = :token")
//    void activateUser(@Param("token") String token);

}
