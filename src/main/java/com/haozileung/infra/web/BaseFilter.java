package com.haozileung.infra.web;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public abstract class BaseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (Initializer.getInjector() != null) {
            Initializer.getInjector().injectMembers(this);
        }
    }
}
