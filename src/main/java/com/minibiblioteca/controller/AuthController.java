package com.minibiblioteca.controller;



import com.minibiblioteca.dto.RegistroDTO;
import com.minibiblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    @GetMapping("/registro")
    public String paginaRegistro(Model model) {
        model.addAttribute("registroDTO", new RegistroDTO());
        return "registro";
    }

    @PostMapping("/registro")
    public String processarRegistro(@Valid @ModelAttribute RegistroDTO registroDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            return "registro";
        }

        try {
            usuarioService.registrar(registroDTO.getNome(), registroDTO.getEmail(), registroDTO.getSenha());
        } catch (Exception e) {
            model.addAttribute("erro", "Não foi possível criar a conta.");
            return "registro";
        }

        return "redirect:/login?sucesso";
    }
}

