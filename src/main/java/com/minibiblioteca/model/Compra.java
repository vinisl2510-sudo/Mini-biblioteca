package com.minibiblioteca.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "compras")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;
    private double precoPago;
    private LocalDateTime datacompra;

    public Compra() {
    }

    public Compra(Usuario usuario,
                  Livro livro,
                  double precoPago,
                  LocalDateTime datacompra) {
        this.usuario = usuario;
        this.livro = livro;
        this.precoPago = precoPago;
        this.datacompra = datacompra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public double getPrecoPago() {
        return precoPago;
    }

    public void setPrecoPago(double precoPago) {
        this.precoPago = precoPago;
    }

    public LocalDateTime getDatacompra() {
        return datacompra;
    }

    public void setDatacompra(LocalDateTime datacompra) {
        this.datacompra = datacompra;
    }
}
