package com.minibiblioteca.service;

import com.minibiblioteca.model.Livro;
import com.minibiblioteca.repository.CompraRepository;
import com.minibiblioteca.repository.FavoritoRepository;
import com.minibiblioteca.repository.LivroRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final FavoritoRepository favoritoRepository;
    private final CompraRepository compraRepository;

    public LivroService(LivroRepository livroRepository, FavoritoRepository favoritoRepository,
                        CompraRepository compraRepository) {
        this.livroRepository = livroRepository;
        this.favoritoRepository = favoritoRepository;
        this.compraRepository = compraRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> buscar(String termo) {
        if (termo == null || termo.isBlank()) {
            return listarTodos();
        }
        return livroRepository.buscarPorTermo(termo.trim());
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado: " + id));
    }

    public Livro criar(String titulo, String autor, String descricao, double preco,
                       String categoria, int estoque, String imagemUrl) {
        Livro livro = new Livro(titulo, autor, descricao, preco, categoria, estoque, imagemUrl);
        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, String titulo, String autor, String descricao, double preco,
                           String categoria, int estoque, String imagemUrl) {
        Livro livro = buscarPorId(id);

        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setDescricao(descricao);
        livro.setPreco(preco);
        livro.setCategoria(categoria);
        livro.setEstoque(estoque);
        livro.setImagemUrl(imagemUrl);

        return livroRepository.save(livro);
    }

    @Transactional
    public void deletar(Long id) {
        Livro livro = buscarPorId(id);
        favoritoRepository.deleteByLivro(livro);
        compraRepository.deleteByLivro(livro);
        livroRepository.deleteById(id);
    }

    public List<Livro> listarPorCategoria(String categoria) {
        return livroRepository.findByCategoria(categoria);
    }

    public List<String> listarCategorias() {
        return livroRepository.listarCategorias();
    }

    public List<Livro> listarTodos(String ordenar) {
        Sort sort = switch (ordenar == null ? "" : ordenar) {
            case "preco-asc" -> Sort.by("preco").ascending();
            case "preco-desc" -> Sort.by("preco").descending();
            case "titulo-az" -> Sort.by("titulo").ascending();
            default -> Sort.unsorted();
        };
        return livroRepository.findAll(sort);
    }
}