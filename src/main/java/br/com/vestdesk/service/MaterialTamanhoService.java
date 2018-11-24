package br.com.vestdesk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.MaterialTamanho;
import br.com.vestdesk.domain.Produto;
import br.com.vestdesk.repository.MaterialTamanhoRepository;
import br.com.vestdesk.service.dto.MaterialTamanhoDTO;
import br.com.vestdesk.service.mapper.MaterialTamanhoMapper;

/**
 * Service for managing MaterialTamanho.
 */
@Service
@Transactional
public class MaterialTamanhoService
{

	private final Logger log = LoggerFactory.getLogger(MaterialTamanhoService.class);

	private final MaterialTamanhoRepository materialTamanhoRepository;

	private final MaterialTamanhoMapper materialTamanhoMapper;

	private final EntityManager em;

	public MaterialTamanhoService(MaterialTamanhoRepository materialTamanhoRepository,
			MaterialTamanhoMapper materialTamanhoMapper, EntityManager em)
	{
		this.materialTamanhoRepository = materialTamanhoRepository;
		this.materialTamanhoMapper = materialTamanhoMapper;
		this.em = em;
	}

	/**
	 * Save a materialTamanho.
	 *
	 * @param materialTamanhoDTO the entity to save
	 * @return the persisted entity
	 */
	public MaterialTamanhoDTO save(MaterialTamanhoDTO materialTamanhoDTO)
	{
		this.log.debug("Request to save MaterialTamanho : {}", materialTamanhoDTO);
		MaterialTamanho materialTamanho = this.materialTamanhoMapper.toEntity(materialTamanhoDTO);
		materialTamanho = this.materialTamanhoRepository.save(materialTamanho);
		return this.materialTamanhoMapper.toDto(materialTamanho);
	}

	public MaterialTamanho save(MaterialTamanho materialTamanho)
	{
		return this.materialTamanhoRepository.save(materialTamanho);

	}

	@Transactional(readOnly = true)
	public Page<MaterialTamanhoDTO> findAll(Pageable pageable)
	{
		this.log.debug("Request to get all MaterialTamanhos");
		return this.materialTamanhoRepository.findAll(pageable).map(this.materialTamanhoMapper::toDto);
	}

	@Transactional(readOnly = true)
	public MaterialTamanhoDTO findOne(Long id)
	{
		this.log.debug("Request to get MaterialTamanho : {}", id);
		MaterialTamanho materialTamanho = this.materialTamanhoRepository.findOne(id);
		return this.materialTamanhoMapper.toDto(materialTamanho);
	}

	public void delete(Long id)
	{
		this.log.debug("Request to delete MaterialTamanho : {}", id);
		this.materialTamanhoRepository.delete(id);
	}

	public void delete(MaterialTamanho materialTamanho)
	{
		this.materialTamanhoRepository.delete(materialTamanho);
	}

	public Set<MaterialTamanho> save(Set<MaterialTamanho> listaMaterialTamanho, Produto p_produto)
	{
		List<MaterialTamanho> lista = new ArrayList<>(listaMaterialTamanho);
		for (int indice = 0; indice < lista.size(); indice++)
		{
			lista.get(indice).setProduto(p_produto);
		}
		this.materialTamanhoRepository.save(lista);
		return listaMaterialTamanho;
	}

	public void delete(Set<MaterialTamanho> lsitaMaterialTamanho)
	{
		for (MaterialTamanho materialTamanho : lsitaMaterialTamanho)
		{
			this.materialTamanhoRepository.delete(materialTamanho);
		}
	}

	public List<MaterialTamanho> obterPeloMaterial(Long id)
	{
		Query query = this.em.createQuery(
				"SELECT materialTamanho FROM MaterialTamanho materialTamanho where material.id = :materialId");
		query.setParameter("materialId", id);

		List<MaterialTamanho> lista = query.getResultList();
		return lista;
	}

}
