package com.bankbot.testdata;

public record CustomerData(
        String name,
        String gender,
        String dateOfBirth,
        String address,
        String city,
        String state,
        String pin,
        String phone,
        String email,
        String password
) {
    public CustomerData withName(String value) {
        return new CustomerData(value, gender, dateOfBirth, address, city, state, pin, phone, email, password);
    }

    public CustomerData withDateOfBirth(String value) {
        return new CustomerData(name, gender, value, address, city, state, pin, phone, email, password);
    }

    public CustomerData withPin(String value) {
        return new CustomerData(name, gender, dateOfBirth, address, city, state, value, phone, email, password);
    }

    public CustomerData withPhone(String value) {
        return new CustomerData(name, gender, dateOfBirth, address, city, state, pin, value, email, password);
    }

    public CustomerData withEmail(String value) {
        return new CustomerData(name, gender, dateOfBirth, address, city, state, pin, phone, value, password);
    }

    public CustomerData withMandatoryText(String value) {
        return new CustomerData(value, gender, dateOfBirth, value, value, value, pin, phone, email, password);
    }
}
