package com.site.model.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang0 on 2016/9/21.
 */
@Setter
@Getter
@ToString
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String mark;

    @ManyToMany()
    @JoinTable(name = "role_member", joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Member> members = new ArrayList<>();
}
