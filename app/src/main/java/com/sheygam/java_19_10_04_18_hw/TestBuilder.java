package com.sheygam.java_19_10_04_18_hw;

public class TestBuilder {
    private String name;
    private String email;
    private String address;
    private String phone;

    private TestBuilder(){

    }

    @Override
    public String toString() {
        return "TestBuilder{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public static class Builder{
        private String b_email;
        private String b_name;
        private String b_address;
        private String b_phone;

        public Builder name(String name){
            this.b_name = name;
            return this;
        }

        public Builder email(String email){
            this.b_email = email;
            return this;
        }
        public Builder address(String address){
            this.b_address = address;
            return this;
        }
        public Builder phone(String phone){
            this.b_phone = phone;
            return this;
        }

        public TestBuilder build(){
            TestBuilder testBuilder = new TestBuilder();
            testBuilder.name = b_name!=null?b_name:"empty";
            testBuilder.email = b_email!=null?b_email:"empty";
            testBuilder.address = b_address!=null?b_address:"empty";
            testBuilder.phone = b_phone!=null?b_phone:"empty";
            return testBuilder;
        }
    }
}
