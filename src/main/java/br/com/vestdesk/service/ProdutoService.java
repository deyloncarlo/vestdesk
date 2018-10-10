package br.com.vestdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.repository.ProdutoRepository;
import br.com.vestdesk.service.dto.ProdutoDTO;
import br.com.vestdesk.service.mapper.ProdutoMapper;

/**
 * Service Interface for managing Produto.
 */
@Service
@Transactional
public class ProdutoService
{

	private final Logger log = LoggerFactory.getLogger(ProdutoService.class);

	private final ProdutoRepository produtoRepository;

	private final ProdutoMapper produtoMapper;

	public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper)
	{
		this.produtoRepository = produtoRepository;
		this.produtoMapper = produtoMapper;
	}

	/**
	 * Save a produto.
	 *
	 * @param produtoDTO the entity to save
	 * @return the persisted entity
	 */
	public ProdutoDTO save(ProdutoDTO produtoDTO)
	{
		this.log.debug("Request to save Produto : {}", produtoDTO);
		Produto produto = this.produtoMapper.toEntity(produtoDTO);
		produto = this.produtoRepository.save(produto);
		return this.produtoMapper.toDto(produto);
	}

	/**
	 * Get all the produtos.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findAll(Pageable pageable)
	{
		this.log.debug("Request to get all Produtos");
		return this.produtoRepository.findAll(pageable).map(this.produtoMapper::toDto);
	}

	/**
	 * Get one produto by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public ProdutoDTO findOne(Long id)
	{
		this.log.debug("Request to get Produto : {}", id);
		Produto produto = this.produtoRepository.findOneWithEagerRelationships(id);
		return this.produtoMapper.toDto(produto);
	}

	/**
	 * Delete the produto by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
		this.log.debug("Request to delete Produto : {}", id);
		this.produtoRepository.delete(id);
	}
}
