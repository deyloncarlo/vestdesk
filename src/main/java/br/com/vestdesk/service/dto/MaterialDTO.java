package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Material entity.
 */
public class MaterialDTO implements Serializable {

    private Long id;

    @NotNull
    private Long oid;

    @NotNull
    private String nome;

    @NotNull
    private BigDecimal preco;

    private Float quantidadeEstoque;

    private Float quantidadeMinima;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
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

    public Float getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Float quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Float getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(Float quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
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
            ", oid=" + getOid() +
            ", nome='" + getNome() + "'" +
            ", preco=" + getPreco() +
            ", quantidadeEstoque=" + getQuantidadeEstoque() +
            ", quantidadeMinima=" + getQuantidadeMinima() +
            "}";
    }
}
