package com.bns.bnsore.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.bns.bnsore.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {
	User findByLoginName(String loginName);
}
