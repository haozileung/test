package com.haozileung.web.domain;

/**
 * Created by haozi on 16-3-7.
 */
public enum DictionaryType {
    MENU(1), ITEM(2);
    private int value;

    DictionaryType(int value) {
        this.value = value;
    }
}
