package com.haozileung.infra.web;

import com.google.inject.Injector
import javax.servlet.Filter
import javax.servlet.FilterConfig

abstract class BaseFilter : Filter {

    override fun init(filterConfig: FilterConfig) {
        if (Initializer.instance != null) {
            (Initializer.instance as Injector).injectMembers(this);
        }
    }
}
