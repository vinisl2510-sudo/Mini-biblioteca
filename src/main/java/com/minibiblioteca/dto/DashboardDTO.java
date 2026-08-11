package com.minibiblioteca.dto;

import com.minibiblioteca.model.Livro;

import java.util.List;

public class DashboardDTO {

    private long totalLivros;
    private long totalUsuarios;
    private long totalCompras;
    private double faturamentoTotal;
    private Livro livroMaisVendido;
    private Livro livroMaisFavoritado;
    private List<Livro> livrosComEstoqueBaixo;


    public long getTotalLivros() {
        return totalLivros;
    }

    public void setTotalLivros(long totalLivros) {
        this.totalLivros = totalLivros;
    }

    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(long totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public long getTotalCompras() {
        return totalCompras;
    }

    public void setTotalCompras(long totalCompras) {
        this.totalCompras = totalCompras;
    }

    public double getFaturamentoTotal() {
        return faturamentoTotal;
    }

    public void setFaturamentoTotal(double faturamentoTotal) {
        this.faturamentoTotal = faturamentoTotal;
    }

    public Livro getLivroMaisVendido() {
        return livroMaisVendido;
    }

    public void setLivroMaisVendido(Livro livroMaisVendido) {
        this.livroMaisVendido = livroMaisVendido;
    }

    public Livro getLivroMaisFavoritado() {
        return livroMaisFavoritado;
    }

    public void setLivroMaisFavoritado(Livro livroMaisFavoritado) {
        this.livroMaisFavoritado = livroMaisFavoritado;
    }

    public List<Livro> getLivrosComEstoqueBaixo() {
        return livrosComEstoqueBaixo;
    }

    public void setLivrosComEstoqueBaixo(List<Livro> livrosComEstoqueBaixo) {
        this.livrosComEstoqueBaixo = livrosComEstoqueBaixo;
    }
}