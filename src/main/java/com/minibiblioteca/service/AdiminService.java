package com.minibiblioteca.service;

import org.springframework.stereotype.Service;

@Service
public class AdiminService {
    private String senha;
    private String email;

    public AdiminService() {
    }

    public AdiminService(String senha, String email) {
        this.senha = senha;
        this.email = email;
    }
}
