package com.avrist.webui.bizservice.webuiloginrequest.model.request;

import com.avrist.webui.bizservice.webuilogincheck.model.request.WebUIloginCheckRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.servlet.http.HttpSession;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WebUIloginRequest extends WebUIloginCheckRequest {
    private Boolean rememberMe;
    private HttpSession session;
}
