package com.yr.management.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author dengbp
 */
public class ERPResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public ERPResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public ERPResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public ERPResponse data(Object data) {
        this.put("data", data);
        return this;
    }


    public ERPResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public ERPResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public ERPResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
