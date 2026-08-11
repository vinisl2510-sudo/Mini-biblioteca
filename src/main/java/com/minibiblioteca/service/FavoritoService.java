package com.minibiblioteca.service;

import com.minibiblioteca.model.Favorito;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import com.minibiblioteca.repository.FavoritoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;

    public FavoritoService(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }
    public boolean alternar(Usuario usuario, Livro livro) {
        Optional<Favorito> existente = favoritoRepository.findByUsuarioAndLivro(usuario, livro);

        if (existente.isPresent()) {
            favoritoRepository.delete(existente.get());
            return false;
        } else {
            favoritoRepository.save(new Favorito(usuario, livro, LocalDateTime.now()));
            return true;
        }
    }

    public List<Favorito> listarPorUsuario(Usuario usuario) {

        return favoritoRepository.findByUsuario(usuario);
    }

    public boolean estaFavoritado(Usuario usuario, Livro livro) {
        return favoritoRepository.existsByUsuarioAndLivro(usuario, livro);
    }

    }