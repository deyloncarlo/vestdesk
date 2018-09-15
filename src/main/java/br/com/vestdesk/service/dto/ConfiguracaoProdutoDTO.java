package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import br.com.vestdesk.domain.enumeration.Tamanho;

/**
 * A DTO for the ConfiguracaoProduto entity.
 */
public class ConfiguracaoProdutoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long oid;

    @NotNull
    private Tamanho tamanho;

    @NotNull
    private BigDecimal preco;

    private Long modeloVestuarioId;

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

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
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

        ConfiguracaoProdutoDTO configuracaoProdutoDTO = (ConfiguracaoProdutoDTO) o;
        if(configuracaoProdutoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), configuracaoProdutoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConfiguracaoProdutoDTO{" +
            "id=" + getId() +
            ", oid=" + getOid() +
            ", tamanho='" + getTamanho() + "'" +
            ", preco=" + getPreco() +
            "}";
    }
}
