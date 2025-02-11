package com.avrist.webui.bizservice.webuiloginprocess;

import com.avrist.core.util.RandomUtil;
import com.avrist.webui.bizservice.BusinessService;
import com.avrist.webui.bizservice.webuilogincheck.WebUiLoginCheckService;
import com.avrist.webui.bizservice.webuiloginprocess.constant.WebUILoginProcessConstant;
import com.avrist.webui.bizservice.webuiloginprocess.model.request.WebUIloginProcessRequest;
import com.avrist.webui.global.constant.AppConstant;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.global.util.JwtUtil;
import com.avrist.webui.service.appconfig.AppConfigService;
import com.avrist.webui.service.appconfig.model.input.AppConfigInput;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebUiLoginProcessService implements BusinessService<WebUIloginProcessRequest, String> {

    private final WebUiLoginCheckService webUILoginCheckService;
    private final AppConfigService appConfigService;

    private String tokenSecretKey;
    private String refreshTokenSecretKey;
    private Integer tokenExpiryInHour;
    private Integer refreshTokenExpiryInDays;

    @Autowired
    public WebUiLoginProcessService(
            WebUiLoginCheckService webUILoginCheckService,
            AppConfigService appConfigService
    ) {
        this.webUILoginCheckService = webUILoginCheckService;
        this.appConfigService = appConfigService;
    }

    private void loadConfig(){
        this.tokenSecretKey = appConfigService.apply(AppConfigInput.builder()
                .configName(AppConstant.TOKEN_SECRET_KEY)
                .configDesc(AppConstant.TOKEN_SECRET_KEY_DESC)
                .configDefaultValue(RandomUtil.randomString(AppConstant.RANDOM_CHARACTER, 100))
                .build()).getConfigValue();
        this.refreshTokenSecretKey = appConfigService.apply(AppConfigInput.builder()
                .configName(AppConstant.REFRESH_TOKEN_SECRET_KEY)
                .configDesc(AppConstant.REFRESH_TOKEN_SECRET_KEY_DESC)
                .configDefaultValue(RandomUtil.randomString(AppConstant.RANDOM_CHARACTER, 100))
                .build()).getConfigValue();
        this.tokenExpiryInHour = Integer.parseInt(appConfigService.apply(AppConfigInput.builder()
                .configName(AppConstant.TOKEN_EXPIRY)
                .configDesc(AppConstant.TOKEN_EXPIRY_DESC)
                .configDefaultValue("24")
                .build()).getConfigValue());
        this.refreshTokenExpiryInDays = Integer.parseInt(appConfigService.apply(AppConfigInput.builder()
                .configName(AppConstant.REFRESH_TOKEN_EXPIRY)
                .configDesc(AppConstant.REFRESH_TOKEN_EXPIRY_DESC)
                .configDefaultValue("30")
                .build()).getConfigValue());
    }

    @Override
    public String execute(WebUIloginProcessRequest input) throws BusinessServiceValidationException {
        var credentials = webUILoginCheckService.execute(input);
        if(ObjectUtils.isNotEmpty(credentials)){
            loadConfig();

            var session = input.getSession();
            session.setAttribute("userId", credentials.getUserId());
            session.setAttribute("token", JwtUtil.generateAccessToken(credentials.getUserId(), tokenExpiryInHour, tokenSecretKey));
            session.setAttribute("rememberMe", input.getRememberMe());
            session.setAttribute("refreshToken", JwtUtil.generateRefreshToken(credentials.getUserId(), refreshTokenExpiryInDays, refreshTokenSecretKey));

            return WebUILoginProcessConstant.REDIRECT_SUCCESS;
        }
        return WebUILoginProcessConstant.REDIRECT_FAILED;
    }
}
