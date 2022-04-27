package com.hospital.management.services.impl;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
public class UUIDGeneration {

    public static final String STAFF_PREFIX = "SF|";

    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return String.format("%s%s", STAFF_PREFIX, uuid.toUpperCase(Locale.ROOT));
    }
}
