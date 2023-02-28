package com.gdsc.solutionChallenge.member.entity;

public enum Role {
    USER, ADMIN;

    public String getKey() {
        return name();
    }
}
