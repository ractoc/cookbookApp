package com.ractoc.cookbook.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
public class BaseController {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        JsonMappingException jme = (JsonMappingException) ex.getCause();
        log.error(jme.getMessage(), jme);
        return jme.getPath().get(0).getFieldName() + " invalid";
    }
}
