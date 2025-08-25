package com.github.echological.sc.global.contract;

import com.github.echological.sc.global.exception.BusinessServiceValidationException;

public interface BusinessServiceContract <I,O> {

    O execute(I input) throws BusinessServiceValidationException;

}
