package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Produto entity.
 */
public class ProdutoDTO implements Serializable {

    private Long id;

    @NotNull
    private String cor;

    @NotNull
    private String tamanho;

    private Long modeloVestuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Long getModeloVestuarioId() {
        return modeloVestuarioId;
    }

    public void setModeloVestuarioId(Long modeloVestuarioId) {
        this.modeloVestuarioId = modeloVestuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProdutoDTO produtoDTO = (ProdutoDTO) o;
        if(produtoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produtoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProdutoDTO{" +
            "id=" + getId() +
            ", cor='" + getCor() + "'" +
            ", tamanho='" + getTamanho() + "'" +
            "}";
    }
}
