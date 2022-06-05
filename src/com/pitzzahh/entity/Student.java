/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pitzzahh.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;

/**
 *
 * @author peter
 */
@Getter
@Setter
@AllArgsConstructor
public class Student {
    private String studentNumber;
    private String name;
    private String age;
    private String address;
    private LocalDate dateOfBirth;
    private String email;
    private String course;

    @Override
    public String toString() {
        return "STUDENT_NUMBER: " + this.studentNumber + "\n" + 
               "NAME          : " + this.name + "\n" + 
               "AGE           : " + this.age + "\n" + 
               "ADDRESS       : " + this.address + "\n" + 
               "COURSE        : " + this.course + "\n";
    }
}
