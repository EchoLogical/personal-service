package com.avrist.sc.global.global.global.contract;

import com.avrist.sc.global.global.global.exception.BusinessServiceValidationException;

public interface BusinessServiceContract <I,O> {

    O execute(I input) throws BusinessServiceValidationException;

}
