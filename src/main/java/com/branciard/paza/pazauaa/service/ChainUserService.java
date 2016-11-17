package com.branciard.paza.pazauaa.service;

import com.branciard.paza.pazauaa.domain.ChainUser;
import com.branciard.paza.pazauaa.repository.ChainUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ChainUser.
 */
@Service
@Transactional
public class ChainUserService {

    private final Logger log = LoggerFactory.getLogger(ChainUserService.class);
    
    @Inject
    private ChainUserRepository chainUserRepository;

    /**
     * Save a chainUser.
     *
     * @param chainUser the entity to save
     * @return the persisted entity
     */
    public ChainUser save(ChainUser chainUser) {
        log.debug("Request to save ChainUser : {}", chainUser);
        ChainUser result = chainUserRepository.save(chainUser);
        return result;
    }

    /**
     *  Get all the chainUsers.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ChainUser> findAll() {
        log.debug("Request to get all ChainUsers");
        List<ChainUser> result = chainUserRepository.findAll();

        return result;
    }

    /**
     *  Get one chainUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ChainUser findOne(Long id) {
        log.debug("Request to get ChainUser : {}", id);
        ChainUser chainUser = chainUserRepository.findOne(id);
        return chainUser;
    }

    /**
     *  Delete the  chainUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChainUser : {}", id);
        chainUserRepository.delete(id);
    }
}
