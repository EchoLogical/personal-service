package com.avrist.webui.endpoint.logger.bizservice.webuiloginrequest;

import com.avrist.webui.datasource.webapp.entity.WebSessionEntity;
import com.avrist.webui.datasource.webapp.repository.WebSessionRepository;
import com.avrist.webui.endpoint.logger.bizservice.BusinessService;
import com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.WebUiLoginCheckService;
import com.avrist.webui.endpoint.logger.bizservice.webuiloginrequest.constant.WebUILoginProcessConstant;
import com.avrist.webui.endpoint.logger.bizservice.webuiloginrequest.model.request.WebUIloginRequest;
import com.avrist.webui.global.constant.AppConstant;
import com.avrist.webui.global.constant.WebSessionConstant;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.endpoint.logger.service.appconfig.impl.AppConfigServiceImpl;
import com.avrist.webui.endpoint.logger.service.appconfig.model.input.AppConfigInput;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WebUILoginRequestService implements BusinessService<WebUIloginRequest, String> {

    private final WebUiLoginCheckService webUILoginCheckService;
    private final AppConfigServiceImpl appConfigServiceImpl;
    private final WebSessionRepository webSessionRepository;

    private Integer sessionExpiryInHour;
    private Integer rememberMeSessionExpiryInHour;

    @Autowired
    public WebUILoginRequestService(
            WebUiLoginCheckService webUILoginCheckService,
            AppConfigServiceImpl appConfigServiceImpl,
            WebSessionRepository webSessionRepository
    ) {
        this.webUILoginCheckService = webUILoginCheckService;
        this.appConfigServiceImpl = appConfigServiceImpl;
        this.webSessionRepository = webSessionRepository;
    }

    private void loadConfig(){
        this.sessionExpiryInHour = Integer.parseInt(appConfigServiceImpl.load(AppConfigInput.builder()
                .configName(AppConstant.SESSION_EXPIRY)
                .configDesc(AppConstant.SESSION_EXPIRY_DESC)
                .configDefaultValue("24")
                .build()).getConfigValue());
        this.rememberMeSessionExpiryInHour = Integer.parseInt(appConfigServiceImpl.load(AppConfigInput.builder()
                .configName(AppConstant.REMEMBER_SESSION_EXPIRY)
                .configDesc(AppConstant.REMEMBER_SESSION_EXPIRY_DESC)
                .configDefaultValue("168")
                .build()).getConfigValue());
    }

    @Override
    public String execute(WebUIloginRequest input) throws BusinessServiceValidationException {
        var credentials = webUILoginCheckService.execute(input);

        var sessionExpiredInHours = this.sessionExpiryInHour;
        if(Boolean.TRUE.equals(input.getRememberMe()))
            sessionExpiredInHours = this.rememberMeSessionExpiryInHour;

        if(ObjectUtils.isNotEmpty(credentials)){
            loadConfig();

            var sessionEntity = webSessionRepository.save(WebSessionEntity.builder()
                            .id(UUID.randomUUID())
                            .username(credentials.getUserId())
                            .fullName(String.format("%s %s", credentials.getFirstName(), credentials.getLastName()).trim())
                            .email(credentials.getEmail())
                            .loginStatus(Boolean.TRUE)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .expiredAt(LocalDateTime.now().plusHours(sessionExpiredInHours))
                    .build());

            var session = input.getSession();
            session.setAttribute(WebSessionConstant.SESSION_ID, sessionEntity.getId());

            return WebUILoginProcessConstant.REDIRECT_SUCCESS;
        }

        return WebUILoginProcessConstant.REDIRECT_FAILED;
    }
}
