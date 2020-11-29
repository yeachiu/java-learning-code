package cn.chiu.haveatry.demo.controller.system;

import cn.chiu.haveatry.demo.dao.system.SysUserMapper;
import cn.chiu.haveatry.demo.pojo.system.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yeachiu on 2019/6/10.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserMapper mapper;

    @GetMapping("/")
    public void home(){
        System.out.println("================================================================>>> Interface is accessed");
    }

    @PostMapping("/add")
    public void addUser(SysUser user){
        mapper.insert(user);
        log.info("insert a user with [[" + user + "]]");
    }

    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable("id") String id,@RequestBody SysUser user){
        // TODO: id与用户id缓存数据作查询，若存在才操作数据库作更新操作
        SysUser updateUser = mapper.getById(id);
        System.out.println(updateUser);
        System.out.println(user);
        BeanUtils.copyProperties(user,updateUser);
        mapper.update(user);
        log.info("update a user with [[" + user + "]]");
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") String id){
        mapper.delete(id);
        log.info("delete a user with id = [[" + id + "]]");
    }

    @GetMapping("/get/{id}")
    public void getById(@PathVariable("id") String id){
        SysUser user = mapper.getById(id);
        log.info("get a user By id = " + id + ", the result is [[" + user + "]]");
    }

    @GetMapping("/get")
    public void findList(){
        List<SysUser> userList = mapper.findAllList();
        log.info("get user list, the result is [[" + userList + "]]");
    }


}
