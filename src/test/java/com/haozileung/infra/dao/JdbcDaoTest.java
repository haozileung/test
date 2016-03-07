package com.haozileung.infra.dao;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.haozileung.web.service.IMyService;
import org.junit.Before;
import org.junit.Test;

public class JdbcDaoTest {

    @Inject
    private IMyService service;

    @Test
    public void testGuiceIOC() {
        service.test();
    }

    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new DaoModule());
        injector.injectMembers(this);
    }
}
