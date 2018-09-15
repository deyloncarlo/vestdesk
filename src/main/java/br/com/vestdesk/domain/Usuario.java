package br.com.vestdesk.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.vestdesk.domain.enumeration.Perfil;

/**
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotNull
	@Column(name = "sigla", nullable = false)
	private String sigla;

	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "senha", nullable = false)
	private String senha;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "perfil", nullable = false)
	private Perfil perfil;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNome()
	{
		return this.nome;
	}

	public Usuario nome(String nome)
	{
		this.nome = nome;
		return this;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getSigla()
	{
		return this.sigla;
	}

	public Usuario sigla(String sigla)
	{
		this.sigla = sigla;
		return this;
	}

	public void setSigla(String sigla)
	{
		this.sigla = sigla;
	}

	public String getEmail()
	{
		return this.email;
	}

	public Usuario email(String email)
	{
		this.email = email;
		return this;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getSenha()
	{
		return this.senha;
	}

	public Usuario senha(String senha)
	{
		this.senha = senha;
		return this;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public Perfil getPerfil()
	{
		return this.perfil;
	}

	public Usuario perfil(Perfil perfil)
	{
		this.perfil = perfil;
		return this;
	}

	public void setPerfil(Perfil perfil)
	{
		this.perfil = perfil;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters
	// and setters here, do not remove

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		Usuario usuario = (Usuario) o;
		if (usuario.getId() == null || getId() == null)
		{
			return false;
		}
		return Objects.equals(getId(), usuario.getId());
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(getId());
	}

	@Override
	public String toString()
	{
		return "Usuario{" + "id=" + getId() + ", nome='" + getNome() + "'" + ", sigla='" + getSigla() + "'"
				+ ", email='" + getEmail() + "'" + ", senha='" + getSenha() + "'" + ", perfil='" + getPerfil() + "'"
				+ "}";
	}
}
