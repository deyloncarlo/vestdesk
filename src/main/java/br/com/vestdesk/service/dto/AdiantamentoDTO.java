package br.com.vestdesk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Adiantamento entity.
 */
public class AdiantamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal valor;

    private Long formaPagementoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getFormaPagementoId() {
        return formaPagementoId;
    }

    public void setFormaPagementoId(Long formaPagamentoId) {
        this.formaPagementoId = formaPagamentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdiantamentoDTO adiantamentoDTO = (AdiantamentoDTO) o;
        if(adiantamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adiantamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdiantamentoDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            "}";
    }
}
