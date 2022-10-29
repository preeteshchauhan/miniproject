package com.example.miniproject;

public class Userholding extends HoldingController {

    private String company;

    private Float totalpromotingholding;

    private Float mutualfunds;

    private Float domesticinstitutes;

    private Float foreigninstitutes;

    public Float retailandother;

    public Userholding(String company, Float totalpromotingholding, Float mutualfunds, Float domesticinstitutes, Float foreigninstitutes, Float retailandother) {
        this.company = company;
        this.totalpromotingholding = totalpromotingholding;
        this.mutualfunds = mutualfunds;
        this.domesticinstitutes = domesticinstitutes;
        this.foreigninstitutes = foreigninstitutes;
        this.retailandother = retailandother;
    }

    public String getCompany() {
        return company;
    }

    public Float getTotalpromotingholding() {
        return totalpromotingholding;
    }

    public Float getMutualfunds() {
        return mutualfunds;
    }

    public Float getDomesticinstitutes() {
        return domesticinstitutes;
    }

    public Float getForeigninstitutes() {
        return foreigninstitutes;
    }

    public Float getRetailandother() {
        return retailandother;
    }
}
