package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the QuantidadeTamanho entity.
 */
public class QuantidadeTamanhoDTO implements Serializable {

    private Long id;

    @NotNull
    private String tamanho;

    @NotNull
    private Float quantidadeMaterial;

    private Long modeloVestuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Float getQuantidadeMaterial() {
        return quantidadeMaterial;
    }

    public void setQuantidadeMaterial(Float quantidadeMaterial) {
        this.quantidadeMaterial = quantidadeMaterial;
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

        QuantidadeTamanhoDTO quantidadeTamanhoDTO = (QuantidadeTamanhoDTO) o;
        if(quantidadeTamanhoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quantidadeTamanhoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuantidadeTamanhoDTO{" +
            "id=" + getId() +
            ", tamanho='" + getTamanho() + "'" +
            ", quantidadeMaterial=" + getQuantidadeMaterial() +
            "}";
    }
}
