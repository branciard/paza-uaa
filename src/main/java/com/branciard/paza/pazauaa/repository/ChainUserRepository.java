package com.branciard.paza.pazauaa.repository;

import com.branciard.paza.pazauaa.domain.ChainUser;

import com.branciard.paza.pazauaa.domain.User;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the ChainUser entity.
 */
@SuppressWarnings("unused")
public interface ChainUserRepository extends JpaRepository<ChainUser,Long> {



    Optional<ChainUser> findOneByEnrollmentId(String enrollmentId);

}
