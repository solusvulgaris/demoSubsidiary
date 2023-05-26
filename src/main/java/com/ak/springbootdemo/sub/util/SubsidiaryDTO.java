package com.ak.springbootdemo.sub.util;

/**
 * Utility class for reading Subsidiaries from JSON file
 * represents DTO - Data Transfer Object - to represent Subsidiary entity based on min essential repesentation
 */
public class SubsidiaryDTO {
    private String innerCode;
    private String address;
    private String name;
    private String phoneNumber;

    protected SubsidiaryDTO() {

    }

    public String getInnerCode() {
        return innerCode;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
