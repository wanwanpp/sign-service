package com.site.model.sign;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by wang0 on 2016/9/23.
 */

@Entity
@Table(name = "buqian")
@Getter
@Setter
public class Buqian {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String date;
    private String reason;
    private Timestamp createTime;
    private Integer status;

    public Buqian(String name, String date,String reason) {
        this.reason = reason;
        this.date = date;
        this.name = name;
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.status = 0;
    }

    public Buqian() {
    }
}
