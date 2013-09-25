package com.bns.bnsore.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.bns.bnsore.entity.Ore;
import com.bns.bnsore.repository.OreDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class OreService {

	private OreDao oreDao;

	public Ore getOre(Long id) {
		return oreDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveOre(Ore entity) {
		oreDao.save(entity);
	}

	@Transactional(readOnly = false)
	public void deleteOre(Long id) {
		oreDao.delete(id);
	}

	public List<Ore> getAllOre() {
		return (List<Ore>) oreDao.findAll();
	}

	public Page<Ore> getUserOre(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Ore> spec = buildSpecification(userId, searchParams);

		return oreDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Ore> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<Ore> spec = DynamicSpecifications.bySearchFilter(filters.values(), Ore.class);
		return spec;
	}

	@Autowired
	public void setOreDao(OreDao oreDao) {
		this.oreDao = oreDao;
	}
}
