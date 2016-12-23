package com.branciard.paza.pazauaa.service;

import com.branciard.paza.pazauaa.domain.ChainUser;
import com.branciard.paza.pazauaa.domain.User;
import com.branciard.paza.pazauaa.domain.enumeration.ChainUserType;
import com.branciard.paza.pazauaa.repository.ChainUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

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
        log.error("Request to save ChainUser : {}", chainUser);
        ChainUser result = chainUserRepository.save(chainUser);
        return result;
    }

    public ChainUser createChainUser(String enrollmentId, String enrollmentSecret, ChainUserType chainUserType, User user){
        Optional<ChainUser> existingChainUser =chainUserRepository.findOneByEnrollmentId(enrollmentId);
        if(existingChainUser.isPresent()){
            //remove it
            chainUserRepository.delete(existingChainUser.get().getId());
        }
        ChainUser newChainUser = new ChainUser();
        newChainUser.setEnrollmentId(enrollmentId);
        newChainUser.setUser(user);
        newChainUser.setActivated(false);
        //do not store enrollmentSecret
        newChainUser.setType(chainUserType);
        chainUserRepository.save(newChainUser);
        return newChainUser;
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
