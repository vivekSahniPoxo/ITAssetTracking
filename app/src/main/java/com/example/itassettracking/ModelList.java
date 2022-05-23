package com.example.itassettracking;

public class ModelList {
    String Name;
    String Id;

    public ModelList(String name, String id) {
        Name = name;
        Id = id;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
