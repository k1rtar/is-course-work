package com.algoforge.apigateway.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.algoforge.common.dto.AlgoUserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class AddHeadersRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders = new HashMap<>();

    public AddHeadersRequestWrapper(HttpServletRequest request, AlgoUserDto userDto) {
        super(request);

        customHeaders.put("X-User-Id", String.valueOf(userDto.getId()));
        customHeaders.put("X-User-Username", userDto.getUsername());
        customHeaders.put("X-User-Email", userDto.getEmail());
        customHeaders.put("X-User-Blocked", String.valueOf(userDto.isBlocked()));
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.addAll(customHeaders.keySet());
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (customHeaders.containsKey(name)) {
            return Collections.enumeration(Arrays.asList(customHeaders.get(name)));
        }
        return super.getHeaders(name);
    }

}

