package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MaterialTamanho entity.
 */
public class MaterialTamanhoDTO implements Serializable {

    private Long id;

    @NotNull
    private Float quantidadeMaterial;

    private Long configuracaoProdutoId;

    private Long materialId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getQuantidadeMaterial() {
        return quantidadeMaterial;
    }

    public void setQuantidadeMaterial(Float quantidadeMaterial) {
        this.quantidadeMaterial = quantidadeMaterial;
    }

    public Long getConfiguracaoProdutoId() {
        return configuracaoProdutoId;
    }

    public void setConfiguracaoProdutoId(Long configuracaoProdutoId) {
        this.configuracaoProdutoId = configuracaoProdutoId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MaterialTamanhoDTO materialTamanhoDTO = (MaterialTamanhoDTO) o;
        if(materialTamanhoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialTamanhoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterialTamanhoDTO{" +
            "id=" + getId() +
            ", quantidadeMaterial=" + getQuantidadeMaterial() +
            "}";
    }
}
