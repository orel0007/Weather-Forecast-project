package main.repo;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * UserRepository: add findByUserName function
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
