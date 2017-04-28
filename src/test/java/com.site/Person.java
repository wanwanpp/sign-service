package com.site;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by 王萍 on 2017/4/27 0027.
 */

@Getter
@Setter
@ToString
public class Person implements Serializable {

    private static final long serialVersionUID = -3562550857760039655L;

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {

    }
}
