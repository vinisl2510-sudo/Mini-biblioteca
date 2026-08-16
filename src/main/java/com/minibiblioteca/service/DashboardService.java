package com.minibiblioteca.service;

import com.minibiblioteca.dto.DashboardDTO;
import com.minibiblioteca.model.Livro;
import com.minibiblioteca.repository.AvaliacaoRepository;
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
    private final AvaliacaoRepository avaliacaoRepository;

    public DashboardService(LivroRepository livroRepository, UsuarioRepository usuarioRepository,
                            CompraRepository compraRepository, FavoritoRepository favoritoRepository,
                            AvaliacaoRepository avaliacaoRepository) {
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
        this.compraRepository = compraRepository;
        this.favoritoRepository = favoritoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
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

        List<Object[]> melhorAvaliados = avaliacaoRepository.livrosMelhorAvaliados();
        if (!melhorAvaliados.isEmpty()) {
            Object[] primeiro = melhorAvaliados.get(0);
            dto.setLivroMelhorAvaliado((Livro) primeiro[0]);
            dto.setNotaMelhorAvaliado((Double) primeiro[1]);
        }

        List<Object[]> piorAvaliados = avaliacaoRepository.livrosPiorAvaliados();
        if (!piorAvaliados.isEmpty()) {
            Object[] primeiro = piorAvaliados.get(0);
            dto.setLivroPiorAvaliado((Livro) primeiro[0]);
            dto.setNotaPiorAvaliado((Double) primeiro[1]);
        }

        return dto;
    }
}