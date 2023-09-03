package com.example.demo.repository;

import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUname(String uname);
    User findByUnameAndPassword(String uname, String password);

    User findByUid(long uid);

    @Modifying
    @Query("UPDATE User u SET u.uname = :newName, u.signature = :newSignature WHERE u.uid = :uid")
    void updateProfile(@Param("uid") long uid, @Param("newName") String newName, @Param("newSignature") String newSignature);
}
