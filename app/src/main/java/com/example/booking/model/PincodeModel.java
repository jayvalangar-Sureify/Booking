package com.example.booking.model;

import java.util.List;

public class PincodeModel {
    private List<PostOffice> PostOffice;
    private String District;
    private String State;

    public List<PostOffice> getPostOffice() {
        return PostOffice;
    }

    public String getDistrict() {
        return District;
    }

    public String getState() {
        return State;
    }

    public static class PostOffice {
        private String Name;
        private String Block;
        private String Division;
        private String Region;
        private String Circle;
        private String Country;
        private String Pincode;
        private String District;
        private String State;

        public String getDistrict(){
            return District;
        }

        public String getState(){
            return State;
        }

        public String getName() {
            return Name;
        }

        public String getBlock() {
            return Block;
        }

        public String getDivision() {
            return Division;
        }

        public String getRegion() {
            return Region;
        }

        public String getCircle() {
            return Circle;
        }

        public String getCountry() {
            return Country;
        }

        public String getPincode() {
            return Pincode;
        }
    }
}