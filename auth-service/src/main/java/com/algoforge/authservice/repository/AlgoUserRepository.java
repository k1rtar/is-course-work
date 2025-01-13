package com.algoforge.authservice.repository;

import java.util.Optional;

import com.algoforge.authservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algoforge.authservice.model.AlgoUser;

@Repository
public interface AlgoUserRepository extends JpaRepository<AlgoUser, Long> {
    
    Optional<AlgoUser> findByUsername(String username);

    Optional<AlgoUser> findByEmail(String password);

    boolean existsByRolesContaining(Role role);

}
