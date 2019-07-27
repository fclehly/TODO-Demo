package com.example.servicezuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        log.info("{} {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        String token = httpServletRequest.getParameter("x-token");
        if (token == null || token.isEmpty()) {
            log.warn("x-token missed");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(403);
            try {
                requestContext.getResponse().getWriter().write("required auth");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        log.debug("finish filter");
        return null;
    }
}
