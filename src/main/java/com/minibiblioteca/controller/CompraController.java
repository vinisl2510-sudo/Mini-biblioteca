package com.minibiblioteca.controller;

import com.minibiblioteca.model.Compra;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.model.Usuario;
import com.minibiblioteca.service.CompraService;
import com.minibiblioteca.service.LivroService;
import com.minibiblioteca.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CompraController {

    private final CompraService compraService;
    private final LivroService livroService;
    private final UsuarioService usuarioService;

    public CompraController(CompraService compraService, LivroService livroService, UsuarioService usuarioService) {
        this.compraService = compraService;
        this.livroService = livroService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/compras/{livroId}")
    public String comprar(@PathVariable Long livroId, @RequestParam(defaultValue = "1") int quantidade,
                          Authentication authentication, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        Livro livro = livroService.buscarPorId(livroId);

        try {
            compraService.comprar(usuario, livro, quantidade);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/livros/" + livroId;
    }

    @GetMapping("/compras")
    public String listar(Model model, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        model.addAttribute("compras", compraService.listarPorUsuario(usuario));
        return "minhas-compras";
    }

    @GetMapping("/compras/{id}")
    public String comprovante(@PathVariable Long id, Model model, Authentication authentication) {
        Usuario usuario = usuarioService.buscarPorEmail(authentication.getName());
        Compra compra = compraService.buscarComprovante(id, usuario);
        model.addAttribute("compra", compra);
        return "comprovante";
    }
}