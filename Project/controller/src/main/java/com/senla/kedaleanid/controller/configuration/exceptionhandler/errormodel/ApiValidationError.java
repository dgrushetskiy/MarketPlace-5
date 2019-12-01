package com.senla.kedaleanid.controller.configuration.exceptionhandler.errormodel;


import com.fasterxml.jackson.annotation.JsonInclude;

class ApiValidationError extends ApiSubError {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String object;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String field;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object rejectedValue;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}