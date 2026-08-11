package com.minibiblioteca.service;

import com.minibiblioteca.dto.DashboardDTO;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.repository.CompraRepository;
import com.minibiblioteca.repository.FavoritoRepository;
import com.minibiblioteca.repository.LivroRepository;
import com.minibiblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final CompraRepository compraRepository;
    private final FavoritoRepository favoritoRepository;

    public DashboardService(LivroRepository livroRepository, UsuarioRepository usuarioRepository,
                            CompraRepository compraRepository, FavoritoRepository favoritoRepository) {
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
        this.compraRepository = compraRepository;
        this.favoritoRepository = favoritoRepository;
    }

    public DashboardDTO gerar() {
        DashboardDTO dto = new DashboardDTO();

        dto.setTotalLivros(livroRepository.count());
        dto.setTotalUsuarios(usuarioRepository.count());
        dto.setTotalCompras(compraRepository.count());

        Double faturamento = compraRepository.faturamentoTotal();
        dto.setFaturamentoTotal(faturamento == null ? 0.0 : faturamento);

        List<Livro> maisVendidos = compraRepository.livrosMaisVendidos();
        dto.setLivroMaisVendido(maisVendidos.isEmpty() ? null : maisVendidos.get(0));

        List<Livro> maisFavoritados = favoritoRepository.livrosMaisFavoritados();
        dto.setLivroMaisFavoritado(maisFavoritados.isEmpty() ? null : maisFavoritados.get(0));

        dto.setLivrosComEstoqueBaixo(livroRepository.findByEstoqueLessThan(3));

        return dto;
    }
}