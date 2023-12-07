package student.examples.uservice.api.business.db.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import student.examples.uservice.api.business.db.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
