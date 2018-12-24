package ru.otus;

class User {
    private Integer id;
    private String name;
    private byte[] payload;

    User(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.payload = new byte[1024*1024*56];
    }

    Integer getId() {
        return id;
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
