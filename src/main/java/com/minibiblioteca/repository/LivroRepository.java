package com.minibiblioteca.repository;

import com.minibiblioteca.model.Livro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query("""
            SELECT l FROM Livro l
            WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termo, '%'))
               OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :termo, '%'))
               OR LOWER(l.categoria) LIKE LOWER(CONCAT('%', :termo, '%'))
            """)
    List<Livro> buscarPorTermo(@Param("termo") String termo);

    List<Livro> findByCategoria(String categoria);

    @Query("SELECT DISTINCT l.categoria FROM Livro l ORDER BY l.categoria")
    List<String> listarCategorias();

    List<Livro> findAll(Sort sort);

    List<Livro> findByEstoqueLessThan(int limite);
}
