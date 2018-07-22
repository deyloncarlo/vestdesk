package br.com.vestdesk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A ModeloVestuario.
 */
@Entity
@Table(name = "modelo_vestuario")
public class ModeloVestuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "preco", precision=10, scale=2, nullable = false)
    private BigDecimal preco;

    @ManyToOne
    private Material material;

    @ManyToOne
    private Modelo modelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public ModeloVestuario preco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Material getMaterial() {
        return material;
    }

    public ModeloVestuario material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public ModeloVestuario modelo(Modelo modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
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
        ModeloVestuario modeloVestuario = (ModeloVestuario) o;
        if (modeloVestuario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modeloVestuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModeloVestuario{" +
            "id=" + getId() +
            ", preco=" + getPreco() +
            "}";
    }
}
