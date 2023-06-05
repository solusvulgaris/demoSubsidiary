package com.ak.springbootdemo.sub.util;

import java.util.Objects;

/**
 * represents DTO - Data Transfer Object - to represent Subsidiary entity based on min essential representation
 */
public class SubsidiaryDTO implements Comparable<SubsidiaryDTO> {
    private String innerCode;
    private String address;
    private String name;
    private String phoneNumber;

    protected SubsidiaryDTO() {

    }

    public SubsidiaryDTO(String innerCode, String address, String name, String phoneNumber) {
        this.innerCode = innerCode;
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubsidiaryDTO that = (SubsidiaryDTO) o;
        return getInnerCode().equals(that.getInnerCode())
                && Objects.equals(getAddress(), that.getAddress())
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getPhoneNumber(), that.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInnerCode(), getAddress(), getName(), getPhoneNumber());
    }

    @Override
    public int compareTo(SubsidiaryDTO o) {
        if (this.equals(o)) return 0;

        if (this.getName().equals(o.getName())) {
            return this.getInnerCode().compareTo(o.getInnerCode());
        }
        return this.getName().compareTo(o.getName());
    }
}
