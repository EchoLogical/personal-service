package com.avrist.webui.bizservice.webuiloginprocess.model.request;

import com.avrist.webui.bizservice.webuilogincheck.model.request.WebUIloginRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.servlet.http.HttpSession;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WebUIloginProcessRequest extends WebUIloginRequest {
    private Boolean rememberMe;
    private HttpSession session;
}
