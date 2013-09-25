package com.bns.bnsore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bns.bnsore.entity.Ore;

public interface OreDao extends PagingAndSortingRepository<Ore, Long>, JpaSpecificationExecutor<Ore> {
	Page<Ore> findByUserId(Long id, Pageable pageRequest);

	@Modifying
	@Query("delete from Ore ore where ore.user.id=?1")
	void deleteByUserId(Long id);
}
