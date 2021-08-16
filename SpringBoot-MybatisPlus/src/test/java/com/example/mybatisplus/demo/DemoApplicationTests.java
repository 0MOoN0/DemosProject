package com.example.mybatisplus.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplus.demo.entiry.User;
import com.example.mybatisplus.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(userMapper.selectById(1));
        ;
    }

    // 测试添加数据，使用数据库自增长ID
    @Test
    public void testInsert() {
        User user = new User();
        user.setAge(18);
        user.setEmail("@qq.com");
        user.setName("zs");
        System.out.println(userMapper.insert(user));
        System.out.println("ID========" + user.getId());
    }

    @Test
    public void testUpdate(){
        // 根据主键进行更新
        User user = new User();
        user.setId(1L);
        user.setAge(20);
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    @Test
    public void testUpdateByQueryWrapper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",1L);
        User user = new User();
        user.setAge(18);
        int update = userMapper.update(user, queryWrapper);
        System.out.println(update);
    }

    @Test
    public void testUpdateByUpdateWrapper(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("age", 20).eq("id",1L);
        userMapper.update(null,updateWrapper);
    }

    @Test
    public void testDeleteByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",6L);
        map.put("age",null);    // 使用AND条件连接
        userMapper.deleteByMap(map);
    }

    @Test
    public void testDeleteByWrapper(){
        User user = new User();
        user.setId(7L);
        user.setName("zs");
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        userMapper.delete(wrapper);
    }

    @Test
    public void testSelectCount(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("age",20);
        System.out.println(userMapper.selectCount(wrapper));
    }

    @Test
    public void testSelectOne(){
        User user = new User();
        user.setName("Jack");
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        System.out.println(userMapper.selectOne(wrapper));
    }

    @Test
    public void testPageSelect(){
        Page<User> page = new Page<>(1,2);  // page实现了Ipage接口
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.gt("age",20);
        IPage<User> userPage = userMapper.selectPage(page, wrapper);
        userPage.getRecords().forEach(System.out::println);
    }
}
