package br.com.vestdesk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Material.
 */
@Entity
@Table(name = "material")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "preco", precision=10, scale=2, nullable = false)
    private BigDecimal preco;

    @NotNull
    @Column(name = "quantidade_minima", nullable = false)
    private Float quantidadeMinima;

    @NotNull
    @Column(name = "quantidade_maxima", nullable = false)
    private Float quantidadeMaxima;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Material nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Material preco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Float getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public Material quantidadeMinima(Float quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
        return this;
    }

    public void setQuantidadeMinima(Float quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public Float getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    public Material quantidadeMaxima(Float quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
        return this;
    }

    public void setQuantidadeMaxima(Float quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Material material = (Material) o;
        if (material.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), material.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Material{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", preco=" + getPreco() +
            ", quantidadeMinima=" + getQuantidadeMinima() +
            ", quantidadeMaxima=" + getQuantidadeMaxima() +
            "}";
    }
}
