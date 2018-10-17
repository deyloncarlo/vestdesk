package br.com.vestdesk.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PedidoItem entity.
 */
public class PedidoItemDTO implements Serializable {

    private Long id;

    private String nomeRoupa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeRoupa() {
        return nomeRoupa;
    }

    public void setNomeRoupa(String nomeRoupa) {
        this.nomeRoupa = nomeRoupa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoItemDTO pedidoItemDTO = (PedidoItemDTO) o;
        if(pedidoItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PedidoItemDTO{" +
            "id=" + getId() +
            ", nomeRoupa='" + getNomeRoupa() + "'" +
            "}";
    }
}
