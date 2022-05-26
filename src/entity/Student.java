/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author peter
 */
public class Student {
    private int studentNumber;
    private String name;
    private byte age;
    private String address;
    private String course;

    public Student(int studentNumber, String name, byte age, String address, String course) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.age = age;
        this.address = address;
        this.course = course;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    
    @Override
    public String toString() {
        return "STUDENT_NUMBER: " + this.studentNumber + "\n" + 
               "NAME          : " + this.name + "\n" + 
               "AGE           : " + this.age + "\n" + 
               "ADDRESS       : " + this.address + "\n" + 
               "COURSE        : " + this.course + "\n";
    }
}
