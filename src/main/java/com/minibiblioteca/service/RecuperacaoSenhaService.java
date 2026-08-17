package com.minibiblioteca.service;

import com.minibiblioteca.model.TokenRecuperacao;
import com.minibiblioteca.model.Usuario;
import com.minibiblioteca.repository.TokenRecuperacaoRepository;
import com.minibiblioteca.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class RecuperacaoSenhaService {

    private final TokenRecuperacaoRepository tokenRepository;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom random = new SecureRandom();

    public RecuperacaoSenhaService(TokenRecuperacaoRepository tokenRepository, UsuarioService usuarioService,
                                   UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void solicitarRecuperacao(String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);

        String token = gerarTokenAleatorio();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(30);

        tokenRepository.save(new TokenRecuperacao(token, usuario, expiracao));

        System.out.println("=================================================");
        System.out.println("LINK DE RECUPERAÇÃO DE SENHA (simulando e-mail):");
        System.out.println("http://localhost:8080/redefinir-senha?token=" + token);
        System.out.println("=================================================");
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        TokenRecuperacao tokenRecuperacao = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido."));

        if (tokenRecuperacao.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Este link expirou. Solicite a recuperação novamente.");
        }

        Usuario usuario = tokenRecuperacao.getUsuario();
        usuario.setSenhaHash(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        tokenRepository.delete(tokenRecuperacao);
    }

    private String gerarTokenAleatorio() {
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}