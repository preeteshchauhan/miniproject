package com.example.miniproject;

public class User extends Statisticstablecontroller {

    private String company;

    private Float weeklow52;

    private Float debt;

    private Float week52high;

    public Float cash;

    private Float marketcap;

    private Float bookvalue;

    private Float facevalue;

    private Float dividend;

    private Float noofshares;

    private Float peratio;

    public User(String company, Float weeklow52, Float debt, Float week52high, Float cash, Float marketcap, Float bookvalue, Float facevalue, Float dividend, Float noofshares, Float peratio) {
        this.company = company;
        this.weeklow52 = weeklow52;
        this.debt = debt;
        this.week52high = week52high;
        this.cash = cash;
        this.marketcap = marketcap;
        this.bookvalue = bookvalue;
        this.facevalue = facevalue;
        this.dividend = dividend;
        this.noofshares = noofshares;
        this.peratio = peratio;
    }

    public String getCompany() {
        return company;
    }

    public Float getWeeklow52() {
        return weeklow52;
    }

    public Float getDebt() {
        return debt;
    }

    public Float getWeek52high() {
        return week52high;
    }

    public Float getCash() {
        return cash;
    }

    public Float getMarketcap() {
        return marketcap;
    }

    public Float getBookvalue() {
        return bookvalue;
    }

    public Float getFacevalue() {
        return facevalue;
    }

    public Float getDividend() {
        return dividend;
    }

    public Float getNoofshares() {
        return noofshares;
    }

    public Float getPeratio() {
        return peratio;
    }
}
