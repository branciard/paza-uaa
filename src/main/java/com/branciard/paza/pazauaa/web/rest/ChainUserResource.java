package com.branciard.paza.pazauaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.branciard.paza.pazauaa.domain.ChainUser;
import com.branciard.paza.pazauaa.service.ChainUserService;
import com.branciard.paza.pazauaa.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ChainUser.
 */
@RestController
@RequestMapping("/api")
public class ChainUserResource {

    private final Logger log = LoggerFactory.getLogger(ChainUserResource.class);
        
    @Inject
    private ChainUserService chainUserService;

    /**
     * POST  /chain-users : Create a new chainUser.
     *
     * @param chainUser the chainUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chainUser, or with status 400 (Bad Request) if the chainUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chain-users")
    @Timed
    public ResponseEntity<ChainUser> createChainUser(@Valid @RequestBody ChainUser chainUser) throws URISyntaxException {
        log.debug("REST request to save ChainUser : {}", chainUser);
        if (chainUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("chainUser", "idexists", "A new chainUser cannot already have an ID")).body(null);
        }
        ChainUser result = chainUserService.save(chainUser);
        return ResponseEntity.created(new URI("/api/chain-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("chainUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chain-users : Updates an existing chainUser.
     *
     * @param chainUser the chainUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chainUser,
     * or with status 400 (Bad Request) if the chainUser is not valid,
     * or with status 500 (Internal Server Error) if the chainUser couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chain-users")
    @Timed
    public ResponseEntity<ChainUser> updateChainUser(@Valid @RequestBody ChainUser chainUser) throws URISyntaxException {
        log.debug("REST request to update ChainUser : {}", chainUser);
        if (chainUser.getId() == null) {
            return createChainUser(chainUser);
        }
        ChainUser result = chainUserService.save(chainUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("chainUser", chainUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chain-users : get all the chainUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chainUsers in body
     */
    @GetMapping("/chain-users")
    @Timed
    public List<ChainUser> getAllChainUsers() {
        log.debug("REST request to get all ChainUsers");
        return chainUserService.findAll();
    }

    /**
     * GET  /chain-users/:id : get the "id" chainUser.
     *
     * @param id the id of the chainUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chainUser, or with status 404 (Not Found)
     */
    @GetMapping("/chain-users/{id}")
    @Timed
    public ResponseEntity<ChainUser> getChainUser(@PathVariable Long id) {
        log.debug("REST request to get ChainUser : {}", id);
        ChainUser chainUser = chainUserService.findOne(id);
        return Optional.ofNullable(chainUser)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /chain-users/:id : delete the "id" chainUser.
     *
     * @param id the id of the chainUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chain-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteChainUser(@PathVariable Long id) {
        log.debug("REST request to delete ChainUser : {}", id);
        chainUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("chainUser", id.toString())).build();
    }

}
