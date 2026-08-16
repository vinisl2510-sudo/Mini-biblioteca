package com.minibiblioteca.repository;

import com.minibiblioteca.model.Avaliacao;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByLivroOrderByDataAvaliacaoDesc(Livro livro);

    Optional<Avaliacao> findByUsuarioAndLivro(Usuario usuario, Livro livro);

    void deleteByLivro(Livro livro);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.livro = :livro")
    Double mediaNotaPorLivro(@org.springframework.data.repository.query.Param("livro") Livro livro);

    @Query("""
            SELECT a.livro, AVG(a.nota) as media
            FROM Avaliacao a
            GROUP BY a.livro
            HAVING COUNT(a) >= 1
            ORDER BY media DESC
            """)
    List<Object[]> livrosMelhorAvaliados();

    @Query("""
            SELECT a.livro, AVG(a.nota) as media
            FROM Avaliacao a
            GROUP BY a.livro
            HAVING COUNT(a) >= 1
            ORDER BY media ASC
            """)
    List<Object[]> livrosPiorAvaliados();
}