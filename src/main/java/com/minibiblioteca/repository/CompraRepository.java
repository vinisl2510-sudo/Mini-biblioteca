package com.minibiblioteca.repository;

import com.minibiblioteca.model.Compra;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CompraRepository extends JpaRepository<Compra, Long> {
    List<Compra> findByUsuario(Usuario usuario);
    boolean existsByUsuarioAndLivro(Usuario usuario, Livro livro);

    @Query("SELECT SUM(c.precoPago) FROM Compra c")
    Double faturamentoTotal();

    @Query("SELECT c.livro FROM Compra c GROUP BY c.livro ORDER BY COUNT(c) DESC")
    List<Livro> livrosMaisVendidos();

    void deleteByLivro(Livro livro);

    Optional<Compra> findByIdAndUsuario(Long id, Usuario usuario);
}
