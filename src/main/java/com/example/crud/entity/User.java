package com.example.crud.entity; // указывает, где лежит данный класс в проекте

import jakarta.persistence.*;
// подключает все @ JPA (framework, который связывает java объекты с таблицами базы данных)

@Entity // говорит, что класс User это сущность и объекты этого класса нужно хранить в базе данных как записи в таблице
@Table(name = "users") // указывает какая таблица базы данных соответствует этому классу
public class User {

    @Id // указывает какое поле является первичным ключом в таблице
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерация ID в бд
    private Long id;
    private String name;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
