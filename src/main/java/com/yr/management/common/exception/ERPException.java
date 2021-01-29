package com.yr.management.common.exception;

/**
 * ERP系统内部异常
 *
 * @author dengbp
 */
public class ERPException extends RuntimeException  {

    private static final long serialVersionUID = -994962710559017255L;

    public ERPException(String message) {
        super(message);
    }
}
