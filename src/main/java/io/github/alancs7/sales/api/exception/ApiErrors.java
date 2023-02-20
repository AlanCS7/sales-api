package io.github.alancs7.sales.api.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class ApiErrors {

    @Getter
    private final List<String> errors;

    public ApiErrors(String message) {
        this.errors = Collections.singletonList(message);
    }
}
