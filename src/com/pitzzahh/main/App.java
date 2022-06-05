/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pitzzahh.main;

import com.pitzzahh.database.DatabaseConnection;

import java.util.Arrays;

/**
 *
 * @author peter john
 */
public class App {
    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String columnNames = Arrays.stream(databaseConnection.getAllTableColumnName())
                                   .map(String::toUpperCase)
                                   .reduce("", String::concat);

        System.out.println(columnNames);
    }
}
