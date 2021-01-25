package com.example.escbasicapp;

import java.util.ArrayList;

public class DummyData {
    public static ArrayList<Contact> contacts= new ArrayList<>();

    static{
        contacts.add(new Contact("이주은","010-2544-9620","sample@hanyang.ac.kr"));
        contacts.add(new Contact("신채영","010-3570-6970","ss@hanyang.ac.kr"));
        contacts.add(new Contact("Name","010-1568-4578","reo@hanyang.ac.kr"));
    }
}
