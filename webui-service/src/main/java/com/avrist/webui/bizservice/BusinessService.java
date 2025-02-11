package com.avrist.webui.bizservice;

import com.avrist.webui.global.exception.BusinessServiceValidationException;

public interface BusinessService <I, O> {
    O execute(I input) throws BusinessServiceValidationException;
}
