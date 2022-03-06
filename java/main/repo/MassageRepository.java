package main.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * MassageRepository: add findByUserName, findTop5ByOrderByIdDesc functions
 */
public interface MassageRepository extends JpaRepository<Massage, Long> {
    List<Massage> findByUserName(String userName);
    List<Massage> findTop5ByOrderByIdDesc();
}
