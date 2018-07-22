package br.com.vestdesk.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cor", nullable = false)
    private String cor;

    @NotNull
    @Column(name = "tamanho", nullable = false)
    private String tamanho;

    @ManyToOne
    private ModeloVestuario modeloVestuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCor() {
        return cor;
    }

    public Produto cor(String cor) {
        this.cor = cor;
        return this;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public Produto tamanho(String tamanho) {
        this.tamanho = tamanho;
        return this;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public ModeloVestuario getModeloVestuario() {
        return modeloVestuario;
    }

    public Produto modeloVestuario(ModeloVestuario modeloVestuario) {
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
        Produto produto = (Produto) o;
        if (produto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", cor='" + getCor() + "'" +
            ", tamanho='" + getTamanho() + "'" +
            "}";
    }
}
