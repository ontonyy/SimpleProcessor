package com.simple.rest.config;

import java.util.Enumeration;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SimpleHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) throws Exception {
        log.info("[PRE-HANDLE][{}] with method: {} and uri: {}, with PARAMS: {}", request, request.getMethod(),
                 request.getRequestURI(), getParameters(request));

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        log.info("[POST-HANDLE][{}]", request);

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex) throws Exception {
        log.info("[AFTER-COMPLETION][{}] ", request);

        if (Objects.nonNull(ex)) {
            log.error("[AFTER-COMPLETION][exception: {}]", ex.getMessage(), ex);
        }

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private String getParameters(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        Enumeration<?> e = request.getParameterNames();

        if (Objects.nonNull(e)) {
            params.append("?");
        }

        while (e.hasMoreElements()) {
            if (params.length() > 1) {
                params.append("&");
            }

            String curr = (String) e.nextElement();
            params.append(curr).append("=");

            if (curr.contains("password") || curr.contains("pass") || curr.contains("pwd")) {
                params.append("*****");
            } else {
                params.append(request.getParameter(curr));
            }
        }

        String ip = getRemoteAddr(request);

        if (Objects.nonNull(ip) && !ip.isEmpty()) {
            params.append("&_psip=").append(ip);
        }

        return params.toString();
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");

        if (ipFromHeader != null && !ipFromHeader.isEmpty()) {
            log.debug(STR."ip from proxy - X-FORWARDED-FOR : \{ipFromHeader}");
            return ipFromHeader;
        }

        return request.getRemoteAddr();
    }
}
