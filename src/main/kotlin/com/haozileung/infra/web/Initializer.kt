package com.haozileung.infra.web;

import com.google.inject.Injector

class Initializer {
    companion object Factory {
        var instance: Injector? = null;
    }
}