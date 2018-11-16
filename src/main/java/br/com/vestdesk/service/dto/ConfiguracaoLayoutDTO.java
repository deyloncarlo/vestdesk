package br.com.vestdesk.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ConfiguracaoLayout entity.
 */
public class ConfiguracaoLayoutDTO implements Serializable {

    private Long id;

    private LocalDate dataCricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataCricao() {
        return dataCricao;
    }

    public void setDataCricao(LocalDate dataCricao) {
        this.dataCricao = dataCricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfiguracaoLayoutDTO configuracaoLayoutDTO = (ConfiguracaoLayoutDTO) o;
        if(configuracaoLayoutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), configuracaoLayoutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConfiguracaoLayoutDTO{" +
            "id=" + getId() +
            ", dataCricao='" + getDataCricao() + "'" +
            "}";
    }
}
