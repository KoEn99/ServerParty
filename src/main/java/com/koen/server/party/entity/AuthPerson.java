package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "auth")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthPerson{
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Column(name = "status")
    @JsonIgnore
    private String status;
    @OneToOne(mappedBy = "authPerson", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private ProfilePerson profilePerson;
    @JsonIgnore
    @OneToMany(mappedBy = "authPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AdsPerson> attachments = new HashSet<AdsPerson>();
    @JsonIgnore
    @OneToMany(mappedBy = "authPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AdsFavorites> attachments1 = new HashSet<AdsFavorites>();
    @JsonIgnore
    @OneToMany(mappedBy = "authPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AdsReviews> attachments2 = new HashSet<AdsReviews>();
    @JsonIgnore
    @OneToMany(mappedBy = "emailFrom", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<RoomChat> attachments3 = new HashSet<RoomChat>();
    @JsonIgnore
    @OneToMany(mappedBy = "authPerson_from", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<MessageChat> attachments5 = new HashSet<MessageChat>();

    public AuthPerson() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProfilePerson getProfilePerson() {
        return profilePerson;
    }

    public void setProfilePerson(ProfilePerson profilePerson) {
        this.profilePerson = profilePerson;
    }

    public Set<AdsPerson> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<AdsPerson> attachments) {
        this.attachments = attachments;
    }

    public Set<AdsFavorites> getAttachments1() {
        return attachments1;
    }

    public void setAttachments1(Set<AdsFavorites> attachments1) {
        this.attachments1 = attachments1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
