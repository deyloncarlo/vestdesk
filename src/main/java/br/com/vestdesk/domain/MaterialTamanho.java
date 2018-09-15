package br.com.vestdesk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MaterialTamanho.
 */
@Entity
@Table(name = "material_tamanho")
public class MaterialTamanho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantidade_material", nullable = false)
    private Float quantidadeMaterial;

    @ManyToOne
    private ConfiguracaoProduto configuracaoProduto;

    @ManyToOne
    private Material material;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getQuantidadeMaterial() {
        return quantidadeMaterial;
    }

    public MaterialTamanho quantidadeMaterial(Float quantidadeMaterial) {
        this.quantidadeMaterial = quantidadeMaterial;
        return this;
    }

    public void setQuantidadeMaterial(Float quantidadeMaterial) {
        this.quantidadeMaterial = quantidadeMaterial;
    }

    public ConfiguracaoProduto getConfiguracaoProduto() {
        return configuracaoProduto;
    }

    public MaterialTamanho configuracaoProduto(ConfiguracaoProduto configuracaoProduto) {
        this.configuracaoProduto = configuracaoProduto;
        return this;
    }

    public void setConfiguracaoProduto(ConfiguracaoProduto configuracaoProduto) {
        this.configuracaoProduto = configuracaoProduto;
    }

    public Material getMaterial() {
        return material;
    }

    public MaterialTamanho material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
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
        MaterialTamanho materialTamanho = (MaterialTamanho) o;
        if (materialTamanho.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), materialTamanho.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MaterialTamanho{" +
            "id=" + getId() +
            ", quantidadeMaterial=" + getQuantidadeMaterial() +
            "}";
    }
}
