package com.hospital.management.enums;

public enum ResponseCode {

    BAD_REQUEST("400", "The request could not be completed due to malformed syntax. Kindly check and try again."),
    INVALID_STAFF_UUID("401", "Staff with this UUID does not exist."),
    INVALID_PATIENT_ID("402", "Patient with this id does not exist."),

    INTERNAL_SERVER_ERROR("5000", "An unexpected error occurred while processing your request. Please try again later.");

    private String code;

    private String description;

    ResponseCode() {
    }

    ResponseCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
