package com.avrist.webui.service;

import com.avrist.webui.global.exception.GeneralServiceValidationException;

public interface GeneralService<I, O> {
    O apply(I input) throws GeneralServiceValidationException;
}
