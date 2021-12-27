package web.model;

import lombok.Data;

@Data
public class User {
    Long id;
    String name;
    String lastName;
    Byte age;

    public User() {
    }

    public User(Long id, String name, String lastName, Byte age) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }
}
