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

    public Compra comprar(Usuario usuario, Livro livro, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        if (livro.getEstoque() < quantidade) {
            throw new IllegalStateException("Estoque insuficiente. Disponível: " + livro.getEstoque());
        }

        livro.setEstoque(livro.getEstoque() - quantidade);

        double valorTotal = livro.getPreco() * quantidade;
        Compra compra = new Compra(usuario, livro, quantidade, valorTotal, LocalDateTime.now());
        return compraRepository.save(compra);
    }

    public List<Compra> listarPorUsuario(Usuario usuario) {
        return compraRepository.findByUsuario(usuario);
    }

    public boolean jaComprou(Usuario usuario, Livro livro) {
        return compraRepository.existsByUsuarioAndLivro(usuario, livro);
    }

    public Compra buscarComprovante(Long id, Usuario usuario) {
        return compraRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new IllegalArgumentException("Comprovante não encontrado."));
    }
}