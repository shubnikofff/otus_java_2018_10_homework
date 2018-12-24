package ru.otus;

class User {
    private Integer id;
    private String name;
    private byte[] bigData;

    User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.bigData = new byte[1024*1024*56];
    }

    Integer getId() {
        return id;
    }

    String getName() {
        return name;
    }
}
