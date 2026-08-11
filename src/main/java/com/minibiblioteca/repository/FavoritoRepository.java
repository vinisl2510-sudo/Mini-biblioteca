package com.minibiblioteca.repository;

import com.minibiblioteca.model.Favorito;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuario(Usuario usuario);
    Optional<Favorito> findByUsuarioAndLivro(Usuario usuario, Livro livro);
    boolean existsByUsuarioAndLivro(Usuario usuario, Livro livro);
}
