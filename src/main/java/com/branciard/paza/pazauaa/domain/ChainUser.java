package com.branciard.paza.pazauaa.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The ChainUser entity.                                                       
 * 
 */
@ApiModel(description = "The ChainUser entity.")
@Entity
@Table(name = "chain_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChainUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "e_cert")
    private String eCert;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String geteCert() {
        return eCert;
    }

    public ChainUser eCert(String eCert) {
        this.eCert = eCert;
        return this;
    }

    public void seteCert(String eCert) {
        this.eCert = eCert;
    }

    public User getUser() {
        return user;
    }

    public ChainUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChainUser chainUser = (ChainUser) o;
        if(chainUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chainUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChainUser{" +
            "id=" + id +
            ", eCert='" + eCert + "'" +
            '}';
    }
}
