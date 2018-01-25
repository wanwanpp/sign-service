package com.site.model.sign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Created by wang0 on 2016/9/13.
 */

@Entity
@Table(name = "sign_records")
@Getter
@Setter
public class SignRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String name;
    @Column(name = "come_time")
    private Timestamp comeTime;
    @Column(name = "leave_time")
    private Timestamp leaveTime;
    @Column(name = "total_mill")
    @JsonIgnore
    private Long totalMill;
    @Column(name = "str_time")
    private String strTime;

    public SignRecords(String name) {
        this.name = name;
        this.comeTime = new Timestamp(System.currentTimeMillis());
    }
    public SignRecords(){}
}
