package br.com.vestdesk.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Cor;
import br.com.vestdesk.domain.MaterialTamanho;
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.domain.VendaAcumulada;
import br.com.vestdesk.domain.enumeration.Modelo;
import br.com.vestdesk.domain.enumeration.Tamanho;
import br.com.vestdesk.repository.ProdutoRepository;
import br.com.vestdesk.service.dto.CorDTO;
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

	private final VendaAcumuladaService vendaAcumuladaService;

	private final MaterialTamanhoService materialTamanhoService;

	private EntityManager em;

	public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper,
			MaterialTamanhoService materialTamanhoService, EntityManager em,
			VendaAcumuladaService vendaAcumuladaService)
	{
		this.produtoRepository = produtoRepository;
		this.produtoMapper = produtoMapper;
		this.materialTamanhoService = materialTamanhoService;
		this.em = em;
		this.vendaAcumuladaService = vendaAcumuladaService;
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
		validarProduto(produtoDTO);
		Set<MaterialTamanho> listaMaterialTamanho = produto.getListaMaterialTamanho();

		if (produto.getId() != null)
		{
			Set<MaterialTamanho> listaMaterialTamanhoRemovidos = new HashSet<>();
			Produto produtoEncontrado = getById(produto.getId());
			for (MaterialTamanho materialTamanho : produtoEncontrado.getListaMaterialTamanho())
			{
				if (!produto.getListaMaterialTamanho().contains(materialTamanho))
				{
					listaMaterialTamanhoRemovidos.add(materialTamanho);
				}
			}
			this.materialTamanhoService.delete(listaMaterialTamanhoRemovidos);
		}

		produto = this.produtoRepository.save(produto);

		this.materialTamanhoService.save(listaMaterialTamanho, produto);
		// criarEntidadeVendaAcumulada(produto);
		return this.produtoMapper.toDto(produto);

	}

	private void validarProduto(ProdutoDTO produtoDTO)
	{

		List<Produto> listaProduto = obterProdutoJaCadastrados(produtoDTO.getDescricao(), produtoDTO.getCodigo(),
				produtoDTO.getCor(), produtoDTO.getModelo(), produtoDTO.getTamanho());
		if (!listaProduto.isEmpty())
		{
			if (produtoDTO.getId() == null || !listaProduto.get(0).getId().equals(produtoDTO.getId()))
			{
				throw new RuntimeException("error.produto.existeProdutoComMesmaConfiguracao");
			}
		}
	}

	private void criarEntidadeVendaAcumulada(Produto produto)
	{
		VendaAcumulada vendaAcumuladaEncontrada = this.vendaAcumuladaService.obterPeloProduto(produto);
		if (vendaAcumuladaEncontrada == null)
		{
			VendaAcumulada vendaAcumulada = new VendaAcumulada();
			vendaAcumulada.setProduto(produto);
			this.vendaAcumuladaService.save(vendaAcumulada);
		}

	}

	/**
	 * Get all the produtos.
	 *
	 * @param pageable the pagination information
	 * @param descricao
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findAll(Pageable pageable, String descricao)
	{
		if (descricao == null)
		{
			descricao = "";
		}

		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);

		int primeiroRegistro = pageable.getPageNumber() * pageable.getPageSize();
		TypedQuery<Produto> query = this.em.createQuery(criteria);
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(pageable.getPageSize());
		List<Produto> listaProduto = query.getResultList();
		Page<Produto> page = new PageImpl<>(listaProduto, pageable, listaProduto.size());

		this.log.debug("Request to get all Produtos");
		return page.map(this.produtoMapper::toDto);
	}

	@Transactional(readOnly = true)
	public List<Produto> obterProdutoJaCadastrados(String descricao, String codigo, CorDTO cor, Modelo modelo,
			Tamanho tamanho)
	{

		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);

		Predicate descricaoPredicate = criteriaBuilder.equal(root.<String>get("descricao"), descricao);
		Predicate codigoPredicate = criteriaBuilder.equal(root.<String>get("codigo"), codigo);
		Predicate modeloPredicate = criteriaBuilder.equal(root.<String>get("modelo"), modelo);
		Predicate tamanhoPredicate = criteriaBuilder.equal(root.<Modelo>get("tamanho"), tamanho);

		Join<Produto, Cor> joinCor = root.join("cor");
		Path<Long> corId = joinCor.get("id");
		Predicate corPredicate = criteriaBuilder.isTrue(corId.in(cor.getId()));

		Predicate descricaoOuCodigo = criteriaBuilder.or(descricaoPredicate, codigoPredicate);
		Predicate modeloTamanhoCor = criteriaBuilder.and(modeloPredicate, tamanhoPredicate, corPredicate);

		Predicate finalPredicate = criteriaBuilder.or(descricaoPredicate, codigoPredicate, modeloTamanhoCor);

		criteria.where(finalPredicate);
		TypedQuery<Produto> query = this.em.createQuery(criteria);
		List<Produto> listaProduto = query.getResultList();

		return listaProduto;
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

	public Produto getById(Long id)
	{
		Produto produto = this.produtoRepository.findOne(id);
		return produto;
	}

	/**
	 * Delete the produto by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
		Produto produtoEncontrado = getById(id);
		if (produtoEncontrado.getListaPedidoItem().isEmpty())
		{
			this.produtoRepository.delete(id);
		}
		else
		{
			throw new RuntimeException("error.produto.existePedidosReferenciandoEsteProduto");
		}
	}

	public Produto save(Produto produto)
	{
		Set<MaterialTamanho> listaMaterialTamanho = produto.getListaMaterialTamanho();

		if (produto.getId() != null)
		{
			Set<MaterialTamanho> listaMaterialTamanhoRemovidos = new HashSet<>();
			Produto produtoEncontrado = getById(produto.getId());
			for (MaterialTamanho materialTamanho : produtoEncontrado.getListaMaterialTamanho())
			{
				if (!produto.getListaMaterialTamanho().contains(materialTamanho))
				{
					listaMaterialTamanhoRemovidos.add(materialTamanho);
				}
			}
			this.materialTamanhoService.delete(listaMaterialTamanhoRemovidos);
		}

		produto = this.produtoRepository.save(produto);

		this.materialTamanhoService.save(listaMaterialTamanho, produto);
		return produto;
	}

	public Produto obterPeloModeloTamanhoCor(Modelo modelo, Tamanho tamanho, Cor cor)
	{
		Query query = this.em.createQuery("SELECT produto FROM Produto produto "
				+ "WHERE modelo = :modeloProduto and tamanho = :tamanhoProduto and cor.id = :corId");
		query.setParameter("modeloProduto", modelo);
		query.setParameter("tamanhoProduto", tamanho);
		query.setParameter("corId", cor.getId());

		List<Produto> listaProduto = query.getResultList();
		if (!listaProduto.isEmpty())
		{
			return listaProduto.get(0);
		}
		return null;
	}
}
