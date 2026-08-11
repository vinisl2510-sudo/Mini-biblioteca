package com.minibiblioteca.controller;

import com.minibiblioteca.model.Usuario;
import com.minibiblioteca.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/perfil/senha")
    public String trocarSenha(@RequestParam String senhaAtual, @RequestParam String novaSenha,
                              Authentication authentication, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());

        try {
            usuarioService.trocarSenha(usuario, senhaAtual, novaSenha);
            model.addAttribute("sucesso", "Senha alterada com sucesso!");
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
        }

        model.addAttribute("usuario", usuario);
        return "perfil";
    }
}