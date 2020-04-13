package cn.itcast.mapper;

import cn.itcast.pojo.Admin;

public interface LoginMapper {
    Admin queryByUserId(String userId);
}
