package com.sheygam.java_19_10_04_18_hw;

import java.io.Serializable;

public class Contact implements Serializable{
    private String name;
    private String email;
    private String phone;
    private String address;

    public Contact() {
    }

    public Contact(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name + "," + email+","+phone+","+address;
    }

    public static Contact newInstance(String str){
        String[] arr = str.split(",");
        return new Contact(arr[0],arr[1],arr[2],arr[3]);
    }
}
