package com.minibiblioteca.controller;

import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import com.minibiblioteca.service.AvaliacaoService;
import com.minibiblioteca.service.LivroService;
import com.minibiblioteca.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final LivroService livroService;
    private final UsuarioService usuarioService;

    public AvaliacaoController(AvaliacaoService avaliacaoService, LivroService livroService,
                               UsuarioService usuarioService) {
        this.avaliacaoService = avaliacaoService;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/livros/{id}/avaliar")
    public String avaliar(@PathVariable Long id, @RequestParam int nota, @RequestParam String comentario,
                          Authentication authentication, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        Livro livro = livroService.buscarPorId(id);

        try {
            avaliacaoService.avaliar(usuario, livro, nota, comentario);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erroAvaliacao", e.getMessage());
        }

        return "redirect:/livros/" + id;
    }
}