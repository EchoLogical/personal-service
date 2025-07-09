package com.avrist.webui.global.contract;

import com.avrist.webui.global.exception.BusinessServiceValidationException;

public interface BusinessServiceContract <I,O> {

    O execute(I input) throws BusinessServiceValidationException;

}
