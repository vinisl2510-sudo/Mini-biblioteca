package com.minibiblioteca.service;

import com.minibiblioteca.model.Compra;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import com.minibiblioteca.repository.CompraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public Compra comprar(Usuario usuario, Livro livro) {
        if (livro.getEstoque() <= 0) {
            throw new IllegalStateException("Livro fora de estoque.");
        }

        livro.setEstoque(livro.getEstoque() - 1);

        Compra compra = new Compra(usuario, livro, livro.getPreco(), LocalDateTime.now());
        return compraRepository.save(compra);
    }

    public List<Compra> listarPorUsuario(Usuario usuario) {
        return compraRepository.findByUsuario(usuario);
    }

    public boolean jaComprou(Usuario usuario, Livro livro) {
        return compraRepository.existsByUsuarioAndLivro(usuario, livro);
    }
}