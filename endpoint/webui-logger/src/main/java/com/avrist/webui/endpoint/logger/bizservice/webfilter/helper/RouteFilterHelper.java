package com.avrist.webui.endpoint.logger.bizservice.webfilter.helper;

import com.avrist.webui.datasource.webapp.repository.WebSessionRepository;
import com.avrist.webui.endpoint.logger.bizservice.webfilter.config.UnfilteredPathConfig;
import com.avrist.webui.global.constant.WebSessionConstant;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RouteFilterHelper {

    private final UnfilteredPathConfig unfilteredPathConfig;
    private final WebSessionRepository webSessionRepository;

    @Autowired
    public RouteFilterHelper(
            UnfilteredPathConfig unfilteredPathConfig,
            WebSessionRepository webSessionRepository) {
        this.unfilteredPathConfig = unfilteredPathConfig;
        this.webSessionRepository = webSessionRepository;
    }

    public boolean unfilteredAccess(HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String unauthenticatedUri : unfilteredPathConfig.getPath()) {
            if (pathMatcher.match(unauthenticatedUri, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIsLoggedIn(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(ObjectUtils.isEmpty(session.getAttribute(WebSessionConstant.SESSION_ID))) return false;

        var sessionId = String.valueOf(session.getAttribute(WebSessionConstant.SESSION_ID));
        var sessionData = webSessionRepository.findById(UUID.fromString(sessionId));

        if(sessionData.isEmpty()) return false;
        if(sessionData.get().getExpiredAt().isBefore(LocalDateTime.now())) return false;
        if(Boolean.FALSE.equals(sessionData.get().getLoginStatus())) return false;

        return true;
    }

}
