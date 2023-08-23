package com.simple.service.api.date;

import java.text.DateFormat;
import java.time.ZonedDateTime;

public interface SimpleDateProviderService {
    DateFormat getDateFormat();
    ZonedDateTime now();
}
