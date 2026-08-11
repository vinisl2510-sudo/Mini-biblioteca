package com.minibiblioteca.controller;

import com.minibiblioteca.model.Livro;
import com.minibiblioteca.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/livros")
public class LivroAdminController {

    private final LivroService livroService;

    public LivroAdminController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("livros", livroService.listarTodos());
        return "admin/livros-admin";
    }

    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("livro", new Livro());
        return "admin/livro-form";
    }

    @PostMapping
    public String criar(@RequestParam String titulo, @RequestParam String autor,
                        @RequestParam String descricao, @RequestParam double preco,
                        @RequestParam String categoria, @RequestParam int estoque,
                        @RequestParam(required = false) String imagemUrl) {
        livroService.criar(titulo, autor, descricao, preco, categoria, estoque, imagemUrl);
        return "redirect:/admin/livros";
    }

    @GetMapping("/{id}/editar")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("livro", livroService.buscarPorId(id));
        return "admin/livro-form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @RequestParam String titulo, @RequestParam String autor,
                            @RequestParam String descricao, @RequestParam double preco,
                            @RequestParam String categoria, @RequestParam int estoque,
                            @RequestParam(required = false) String imagemUrl) {
        livroService.atualizar(id, titulo, autor, descricao, preco, categoria, estoque, imagemUrl);
        return "redirect:/admin/livros";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        livroService.deletar(id);
        return "redirect:/admin/livros";
    }
}