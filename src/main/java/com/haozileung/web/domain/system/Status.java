package com.haozileung.web.domain.system;

/**
 * Created by haozi on 16-3-7.
 */
public enum Status {

    ENABLED(1), DISABLED(2);

    private int value;

    Status(int value) {
        this.value = value;
    }

}
