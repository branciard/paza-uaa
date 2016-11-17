package com.branciard.paza.pazauaa.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.branciard.paza.pazauaa.domain.enumeration.ChainUserType;

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

    @NotNull
    @Column(name = "enrollment_id", nullable = false)
    private String enrollmentId;

    @Column(name = "enrollment_secret")
    private String enrollmentSecret;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ChainUserType type;

    @Column(name = "activated")
    private Boolean activated;

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

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public ChainUser enrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
        return this;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getEnrollmentSecret() {
        return enrollmentSecret;
    }

    public ChainUser enrollmentSecret(String enrollmentSecret) {
        this.enrollmentSecret = enrollmentSecret;
        return this;
    }

    public void setEnrollmentSecret(String enrollmentSecret) {
        this.enrollmentSecret = enrollmentSecret;
    }

    public ChainUserType getType() {
        return type;
    }

    public ChainUser type(ChainUserType type) {
        this.type = type;
        return this;
    }

    public void setType(ChainUserType type) {
        this.type = type;
    }

    public Boolean isActivated() {
        return activated;
    }

    public ChainUser activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
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
            ", enrollmentId='" + enrollmentId + "'" +
            ", enrollmentSecret='" + enrollmentSecret + "'" +
            ", type='" + type + "'" +
            ", activated='" + activated + "'" +
            ", eCert='" + eCert + "'" +
            '}';
    }
}
