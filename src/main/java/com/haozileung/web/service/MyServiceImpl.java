package com.haozileung.web.service;

import com.google.inject.Inject;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.dal.build.Criteria;
import com.haozileung.web.domain.User;

public class MyServiceImpl implements IMyService {
	@Inject
	private JdbcDao dao;

	@Override
	public void test() {
		System.out.println(dao.queryList(Criteria.select(User.class)));
	}

}
