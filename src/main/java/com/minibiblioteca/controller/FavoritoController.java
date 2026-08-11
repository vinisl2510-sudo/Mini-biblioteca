package com.minibiblioteca.controller;

import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import com.minibiblioteca.service.FavoritoService;
import com.minibiblioteca.service.LivroService;
import com.minibiblioteca.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FavoritoController {

    private final FavoritoService favoritoService;
    private final LivroService livroService;
    private final UsuarioService usuarioService;

    public FavoritoController(FavoritoService favoritoService, LivroService livroService, UsuarioService usuarioService) {
        this.favoritoService = favoritoService;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/favoritos/{livroId}")
    public String alternar(@PathVariable Long livroId, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        Livro livro = livroService.buscarPorId(livroId);
        favoritoService.alternar(usuario, livro);
        return "redirect:/livros/" + livroId;
    }

    @GetMapping("/favoritos")
    public String listar(Model model, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        model.addAttribute("favoritos", favoritoService.listarPorUsuario(usuario));
        return "favoritos";
    }
}