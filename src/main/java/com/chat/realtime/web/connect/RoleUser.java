package com.chat.realtime.web.controller;

import java.security.Principal;

public class RoleUser implements Principal {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleUser(String name) {
        this.name = name;
    }
}
