package cn.itcast.service.Impl;

import cn.itcast.mapper.LoginMapper;
import cn.itcast.pojo.Admin;
import cn.itcast.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("loginServices")
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    public LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = null;
        Admin loginAdmin = loginMapper.queryByUserId(userId);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SHI"));
        user = new User(userId,loginAdmin.getPassword(), grantedAuthorities);

        System.out.println(user.getUsername()+"==================================================" + user.getPassword());

        return user;
    }

}
