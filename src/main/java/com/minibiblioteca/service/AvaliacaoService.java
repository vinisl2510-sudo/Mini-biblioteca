package com.minibiblioteca.service;

import com.minibiblioteca.model.Avaliacao;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import com.minibiblioteca.repository.AvaliacaoRepository;
import com.minibiblioteca.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final CompraRepository compraRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, CompraRepository compraRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.compraRepository = compraRepository;
    }

    public Avaliacao avaliar(Usuario usuario, Livro livro, int nota, String comentario) {
        boolean jaComprou = compraRepository.existsByUsuarioAndLivro(usuario, livro);
        if (!jaComprou) {
            throw new IllegalStateException("Só é possível avaliar livros que você já comprou.");
        }

        Optional<Avaliacao> existente = avaliacaoRepository.findByUsuarioAndLivro(usuario, livro);

        Avaliacao avaliacao = existente.orElse(new Avaliacao());
        avaliacao.setUsuario(usuario);
        avaliacao.setLivro(livro);
        avaliacao.setNota(nota);
        avaliacao.setComentario(comentario);
        avaliacao.setDataAvaliacao(LocalDateTime.now());

        return avaliacaoRepository.save(avaliacao);
    }

    public List<Avaliacao> listarPorLivro(Livro livro) {
        return avaliacaoRepository.findByLivroOrderByDataAvaliacaoDesc(livro);
    }

    public Double mediaNota(Livro livro) {
        return avaliacaoRepository.mediaNotaPorLivro(livro);
    }
}