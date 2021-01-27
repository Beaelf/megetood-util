package com.megetood.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * User Model
 *
 * @author Lei Chengdong
 * @date 2021/1/4
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    private String name;
    private Integer sex;
    private Date birth;

    public User(String name, Integer sex) {
        this.name = name;
        this.sex = sex;
    }

    public User(String name, Date birth) {
        this.name = name;
        this.birth = birth;
    }
}
