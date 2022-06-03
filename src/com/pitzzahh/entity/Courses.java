package com.pitzzahh.entity;

public enum Courses {

    BSIT("Bachelor of Science in Information Technology"),
    BSCS("Bachelor of Science in Computer Science"),
    BSIS("Bachelor of Science in Information Systems"),
    BSBA("Bachelor of Science in Business Administration"),
    BSA("Bachelor of Science in Accountancy"),
    BSAIS("Bachelor of Science in Accounting Information System"),
    BSRTCS("Bachelor of Science in Retail Technology and Consumer Science"),
    BSMA("Bachelor of Science in Management Accounting"),
    BSHM("Bachelor of Science in Hospitality Management"),
    BSCM("Bachelor of Science in Culinary Management"),
    BSTM("Bachelor of Science in Tourism Management"),
    BSCPE("Bachelor of Science in Computer Engineering"),
    BMMA("Bachelor of Multimedia Arts"),
    BACOMM("Bachelor of Arts in Communication");

    private final String description;

    Courses(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
