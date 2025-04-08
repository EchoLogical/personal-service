package com.avrist.webui.bizservice.webfilter;

import com.avrist.core.constant.AVRStatus;
import com.avrist.webui.bizservice.BusinessService;
import com.avrist.webui.bizservice.webfilter.helper.RouteFilterHelper;
import com.avrist.webui.bizservice.webfilter.model.input.WebFilterInput;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.global.exception.GeneralServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WebFilterService implements BusinessService<WebFilterInput, Boolean> {

    private final RouteFilterHelper routeFilterHelper;

    @Autowired
    public WebFilterService(RouteFilterHelper routeFilterHelper) {
        this.routeFilterHelper = routeFilterHelper;
    }

    @Override
    public Boolean execute(WebFilterInput input) throws GeneralServiceValidationException {
        // for public access
        if(routeFilterHelper.unfilteredAccess(input.getRequest()))
            return true;

        // check if user are not logged in
        if(!routeFilterHelper.checkIsLoggedIn(input.getRequest()))
            throw new BusinessServiceValidationException(
                    AVRStatus.UNAUTHORIZED_REDIRECT.getCode(),
                    AVRStatus.UNAUTHORIZED_REDIRECT.getStatus(),
                    HttpStatus.UNAUTHORIZED,
                    Stream.of("Access Denied.").collect(Collectors.toList()));

        return false;
    }
}
