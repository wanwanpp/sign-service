package com.site.repository;

import com.site.model.login.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wang0 on 2016/9/13.
 */
public interface MemberRepo extends JpaRepository<Member, Long> {

    @Query("select m.name from Member m where m.grade =?1")
    List<String> findNameByGrade(int grade);

    @Query("select m.name from Member m")
    List<String> findAllNames();

    /**
     * 使用HQL进行某个表的多字段查询
     */
    @Query("select m.name,m.isstart from Member m")
    List<Object[]> findNamesAndIsstart();

//    查找已经签到的名字
    @Query("select m.name from Member m where m.isstart=1")
    List<String> findNamesStart();

    @Query("select m.pwd from Member m where m.name =?1")
    String findPwdByName(String name);

    @Modifying
    @Transactional
    @Query("update Member m set m.pwd=?1 where m.name=?2")
    int setPwd(String pwd, String name);

    Member findByName(String name);
}
