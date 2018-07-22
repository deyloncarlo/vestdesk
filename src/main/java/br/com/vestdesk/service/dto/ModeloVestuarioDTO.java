package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ModeloVestuario entity.
 */
public class ModeloVestuarioDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal preco;

    private Long materialId;

    private Long modeloId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getModeloId() {
        return modeloId;
    }

    public void setModeloId(Long modeloId) {
        this.modeloId = modeloId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModeloVestuarioDTO modeloVestuarioDTO = (ModeloVestuarioDTO) o;
        if(modeloVestuarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modeloVestuarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModeloVestuarioDTO{" +
            "id=" + getId() +
            ", preco=" + getPreco() +
            "}";
    }
}
