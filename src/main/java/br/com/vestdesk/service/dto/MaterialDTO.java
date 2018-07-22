package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Material entity.
 */
public class MaterialDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private BigDecimal preco;

    @NotNull
    private Float quantidadeMinima;

    @NotNull
    private Float quantidadeMaxima;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Float getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(Float quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public Float getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    public void setQuantidadeMaxima(Float quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaterialDTO materialDTO = (MaterialDTO) o;
        if(materialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterialDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", preco=" + getPreco() +
            ", quantidadeMinima=" + getQuantidadeMinima() +
            ", quantidadeMaxima=" + getQuantidadeMaxima() +
            "}";
    }
}
