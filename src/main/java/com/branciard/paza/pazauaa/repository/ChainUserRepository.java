package com.branciard.paza.pazauaa.repository;

import com.branciard.paza.pazauaa.domain.ChainUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ChainUser entity.
 */
@SuppressWarnings("unused")
public interface ChainUserRepository extends JpaRepository<ChainUser,Long> {

}
