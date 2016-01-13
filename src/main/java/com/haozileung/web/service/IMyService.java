package com.haozileung.web.service;

import com.google.inject.ImplementedBy;

@ImplementedBy(MyServiceImpl.class)
public interface IMyService {

	public void test();
}
