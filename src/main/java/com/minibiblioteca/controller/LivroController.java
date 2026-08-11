package com.minibiblioteca.controller;

import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import com.minibiblioteca.service.CompraService;
import com.minibiblioteca.service.FavoritoService;
import com.minibiblioteca.service.LivroService;
import com.minibiblioteca.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LivroController {

    private final LivroService livroService;
    private final UsuarioService usuarioService;
    private final FavoritoService favoritoService;
    private final CompraService compraService;

    public LivroController(LivroService livroService, UsuarioService usuarioService,
                           FavoritoService favoritoService, CompraService compraService) {
        this.livroService = livroService;
        this.usuarioService = usuarioService;
        this.favoritoService = favoritoService;
        this.compraService = compraService;
    }

    @GetMapping("/livros")
    public String listar(@RequestParam(value = "q", required = false) String q,
                         @RequestParam(value = "categoria", required = false) String categoria,
                         @RequestParam(value = "ordenar", required = false) String ordenar,
                         Model model) {

        List<Livro> livros;
        if (categoria != null && !categoria.isBlank()) {
            livros = livroService.listarPorCategoria(categoria);
        } else if (q != null && !q.isBlank()) {
            livros = livroService.buscar(q);
        } else {
            livros = livroService.listarTodos(ordenar);
        }

        model.addAttribute("livros", livros);
        model.addAttribute("termoBusca", q == null ? "" : q);
        model.addAttribute("categoriaAtiva", categoria == null ? "" : categoria);
        model.addAttribute("ordenarAtivo", ordenar == null ? "" : ordenar);
        model.addAttribute("categorias", livroService.listarCategorias());

        return "livros";
    }

    @GetMapping("/livros/{id}")
    public String detalhe(@PathVariable Long id, Model model, Authentication authentication) {
        Livro livro = livroService.buscarPorId(id);
        model.addAttribute("livro", livro);

        if (authentication != null) {
            Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
            model.addAttribute("favoritado", favoritoService.estaFavoritado(usuario, livro));
            model.addAttribute("comprado", compraService.jaComprou(usuario, livro));
        }

        return "livro-detalhe";
    }
}
