package br.com.vestdesk.service.dto;

import java.io.Serializable;

public class RelatorioVendaItemDTO implements Serializable
{

	private static final long serialVersionUID = 1L;

	private ProdutoDTO produto;
	private Integer amount;

	public ProdutoDTO getProduto()
	{
		return this.produto;
	}

	public void setProduto(ProdutoDTO produto)
	{
		this.produto = produto;
	}

	public Integer getAmount()
	{
		return this.amount;
	}

	public void setAmount(Integer amount)
	{
		this.amount = amount;
	}

}
