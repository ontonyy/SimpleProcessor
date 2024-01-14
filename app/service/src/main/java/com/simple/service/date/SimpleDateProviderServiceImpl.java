package com.simple.service.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.simple.service.api.date.SimpleDateProviderService;

@Component
public class SimpleDateProviderServiceImpl implements SimpleDateProviderService {
    @Override
    public DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }
}
