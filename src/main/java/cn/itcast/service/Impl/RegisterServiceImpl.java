package cn.itcast.service.Impl;

import cn.itcast.mapper.RegisterMapper;
import cn.itcast.pojo.Admin;
import cn.itcast.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    public RegisterMapper registerMapper;

    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void insertUser(Admin admin) {
        String encode = bCryptPasswordEncoder.encode(admin.getPassword());
        admin.setPassword(encode);
        registerMapper.insertUser(admin);

    }
}
