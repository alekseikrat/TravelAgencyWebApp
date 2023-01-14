package ua.edu.znu.travelagencyweb.utils;

import ua.edu.znu.travelagencyweb.service.ClientDaoImpl;

import javax.persistence.Persistence;
/**
 * Use for DDL-script for drop tables and create tables generation.
 * You must enable properties for schema generation in the persistence.xml.
 */
public class GenerateDDLSchema {
    public static void main(String[] args) {
        Persistence.generateSchema("travelagencyPU", null);
    }
}