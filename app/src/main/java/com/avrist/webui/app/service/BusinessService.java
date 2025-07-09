package com.avrist.webui.app.service;

import com.avrist.webui.global.exception.BusinessServiceValidationException;

public interface BusinessService<I, O> {
    O execute(I input) throws BusinessServiceValidationException;
}
