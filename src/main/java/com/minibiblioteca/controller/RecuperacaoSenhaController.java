package com.minibiblioteca.controller;

import com.minibiblioteca.service.RecuperacaoSenhaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecuperacaoSenhaController {

    private final RecuperacaoSenhaService recuperacaoSenhaService;

    public RecuperacaoSenhaController(RecuperacaoSenhaService recuperacaoSenhaService) {
        this.recuperacaoSenhaService = recuperacaoSenhaService;
    }

    @GetMapping("/esqueci-senha")
    public String paginaEsqueciSenha() {
        return "esqueci-senha";
    }

    @PostMapping("/esqueci-senha")
    public String processarEsqueciSenha(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            recuperacaoSenhaService.solicitarRecuperacao(email);
        } catch (Exception e) {
            // Não revela se o e-mail existe ou não, por segurança
        }

        redirectAttributes.addFlashAttribute("sucesso",
                "Se esse e-mail existir na nossa base, um link de recuperação foi gerado (veja o console).");
        return "redirect:/esqueci-senha";
    }

    @GetMapping("/redefinir-senha")
    public String paginaRedefinirSenha(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "redefinir-senha";
    }

    @PostMapping("/redefinir-senha")
    public String processarRedefinirSenha(@RequestParam String token, @RequestParam String novaSenha,
                                          RedirectAttributes redirectAttributes) {
        try {
            recuperacaoSenhaService.redefinirSenha(token, novaSenha);
            redirectAttributes.addFlashAttribute("sucesso", "Senha redefinida com sucesso! Faça login.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/redefinir-senha?token=" + token;
        }
    }
}