package br.com.vestdesk.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vestdesk.domain.Cor;
import br.com.vestdesk.domain.Material;
import br.com.vestdesk.domain.MaterialTamanho;
import br.com.vestdesk.repository.MaterialRepository;
import br.com.vestdesk.service.dto.MaterialDTO;
import br.com.vestdesk.service.mapper.MaterialMapper;

/**
 * Service Interface for managing Material.
 */
@Service
@Transactional
public class MaterialService
{

	private final Logger log = LoggerFactory.getLogger(MaterialService.class);

	private final MaterialRepository materialRepository;

	private final MaterialMapper materialMapper;

	private final MaterialTamanhoService materialTamanhoService;

	private final CorService corService;

	private EntityManager em;

	public MaterialService(MaterialRepository materialRepository, MaterialMapper materialMapper, CorService corService,
			MaterialTamanhoService materialTamanhoService, EntityManager em)
	{
		this.materialRepository = materialRepository;
		this.materialMapper = materialMapper;
		this.corService = corService;
		this.materialTamanhoService = materialTamanhoService;
		this.em = em;
	}

	/**
	 * Save a material.
	 *
	 * @param materialDTO the entity to save
	 * @return the persisted entity
	 */
	public MaterialDTO save(MaterialDTO materialDTO)
	{
		this.log.debug("Request to save Material : {}", materialDTO);
		Material material = this.materialMapper.toEntity(materialDTO);

		Cor v_cor = this.corService.getById(material.getCor().getId());

		material.setCor(v_cor);
		material = this.materialRepository.save(material);
		return this.materialMapper.toDto(material);
	}

	/**
	 * Get all the materials.
	 *
	 * @param pageable the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<MaterialDTO> findAll(Pageable pageable, String nome)
	{
		if (nome == null)
		{
			nome = "";
		}

		Query query = this.em.createQuery("SELECT material FROM Material material WHERE nome LIKE :nomeMaterial");
		query.setParameter("nomeMaterial", "%" + nome + "%");

		List<Material> listaMaterial = query.getResultList();
		Page<Material> page = new PageImpl<>(listaMaterial, pageable, listaMaterial.size());

		this.log.debug("Request to get all Materials");
		return page.map(this.materialMapper::toDto);
	}

	/**
	 * Get one material by id.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public MaterialDTO findOne(Long id)
	{
		this.log.debug("Request to get Material : {}", id);
		Material material = this.materialRepository.findOne(id);
		return this.materialMapper.toDto(material);
	}

	/**
	 * Delete the material by id.
	 *
	 * @param id the id of the entity
	 */
	public void delete(Long id)
	{
		this.log.debug("Request to delete Material : {}", id);
		List<MaterialTamanho> listaEncontrada = this.materialTamanhoService.obterPeloMaterial(id);
		if (listaEncontrada.isEmpty())
		{
			this.materialRepository.delete(id);
		}
		else
		{
			throw new RuntimeException("error.material.existeProdutosReferenciandoEsteMaterial");
		}
	}
}
