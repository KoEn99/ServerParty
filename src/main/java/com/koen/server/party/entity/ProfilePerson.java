package com.koen.server.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "profile")
public class ProfilePerson {
    @Id
    @Column(name = "authperson_id", unique = true, nullable = true)
    private Long id;
    @JsonIgnore
    @MapsId
    @OneToOne
    private AuthPerson authPerson;
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Column(name = "surname", nullable = false, length = 30)
    private String surname;
    @Column(name = "city", nullable = false, length = 30)
    private String city;
    @Column(name = "middle_name", nullable = false, length = 30)
    private String middleName;
    @Column(name = "number_phone", nullable = false, length = 15)
    private String numberPhone;
    @Column(name = "avatar", nullable = true, length = 100)
    private String avatar;
    public ProfilePerson(){

    }
    public AuthPerson getAuthPerson() {
        return authPerson;
    }

    public void setAuthPerson(AuthPerson authPerson) {
        this.authPerson = authPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
