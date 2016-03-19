package com.haozileung.service;

import com.alibaba.fastjson.JSON;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.haozileung.infra.pager.PageResult;
import com.haozileung.web.domain.dictionary.Dictionary;
import com.haozileung.web.init.DaoModule;
import com.haozileung.web.service.dictionary.IDictionaryService;
import org.junit.Before;
import org.junit.Test;

public class ServiceTest {

    @Inject
    private IDictionaryService service;

    @Test
    public void testGuiceIOC() {
        Dictionary dictionary = new Dictionary();
        dictionary.setDictionaryId(1L);
        PageResult<Dictionary> list = service.query(dictionary, 1, 20);
        System.out.println(JSON.toJSON(list));
    }

    @Before
    public void setUp() {
        Injector injector = Guice.createInjector(new DaoModule());
        injector.injectMembers(this);
    }
}
