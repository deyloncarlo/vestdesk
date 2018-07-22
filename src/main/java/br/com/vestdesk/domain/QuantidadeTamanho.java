package br.com.vestdesk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A QuantidadeTamanho.
 */
@Entity
@Table(name = "quantidade_tamanho")
public class QuantidadeTamanho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tamanho", nullable = false)
    private String tamanho;

    @NotNull
    @Column(name = "quantidade_material", nullable = false)
    private Float quantidadeMaterial;

    @ManyToOne
    private ModeloVestuario modeloVestuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTamanho() {
        return tamanho;
    }

    public QuantidadeTamanho tamanho(String tamanho) {
        this.tamanho = tamanho;
        return this;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Float getQuantidadeMaterial() {
        return quantidadeMaterial;
    }

    public QuantidadeTamanho quantidadeMaterial(Float quantidadeMaterial) {
        this.quantidadeMaterial = quantidadeMaterial;
        return this;
    }

    public void setQuantidadeMaterial(Float quantidadeMaterial) {
        this.quantidadeMaterial = quantidadeMaterial;
    }

    public ModeloVestuario getModeloVestuario() {
        return modeloVestuario;
    }

    public QuantidadeTamanho modeloVestuario(ModeloVestuario modeloVestuario) {
        this.modeloVestuario = modeloVestuario;
        return this;
    }

    public void setModeloVestuario(ModeloVestuario modeloVestuario) {
        this.modeloVestuario = modeloVestuario;
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
        QuantidadeTamanho quantidadeTamanho = (QuantidadeTamanho) o;
        if (quantidadeTamanho.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quantidadeTamanho.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuantidadeTamanho{" +
            "id=" + getId() +
            ", tamanho='" + getTamanho() + "'" +
            ", quantidadeMaterial=" + getQuantidadeMaterial() +
            "}";
    }
}
