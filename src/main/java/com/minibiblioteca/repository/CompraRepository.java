package com.minibiblioteca.repository;

import com.minibiblioteca.model.Compra;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByUsuario(Usuario usuario);
    boolean existsByUsuarioAndLivro(Usuario usuario, Livro livro);

}
