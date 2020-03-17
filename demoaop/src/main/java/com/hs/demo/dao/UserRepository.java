package com.hs.demo.dao;

import com.hs.demo.pojo.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SysUser, Long> {
    SysUser findSysUserByUserNameAndEmail(String userName, String email);
    SysUser findSysUserByUserName(String userName);
}
