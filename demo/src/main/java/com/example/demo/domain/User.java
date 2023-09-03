package com.example.demo.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;

    private String uname;

    private String password;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    private String signature;  //个人签名

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }
    public String getSignature(){
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
