package com.example.mybatisplus.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.demo.entiry.User;
import com.example.mybatisplus.demo.mapper.UserMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Leon
 */
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService{

    public void test(){
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        Page<User> page = new Page<>();
        Page<User> selectPage = getBaseMapper().selectPage(page, queryWrapper);
    }

    Boolean deleteByIds(Long[] ids){
        List<User> list = Arrays.stream(ids).map(id -> {
            User user = new User();
            user.setId(id);
            return user;
        }).collect(Collectors.toList());
        return updateBatchById(list);
    }

}
