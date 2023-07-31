package com.simple.service.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

import com.simple.service.api.date.SimpleDateProviderService;

@Service
public class SimpleDateProviderServiceImpl implements SimpleDateProviderService {
    @Override
    public DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    @Override
    public ZonedDateTime currentDate() {
        return ZonedDateTime.now();
    }
}