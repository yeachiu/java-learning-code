package cn.chiu.haveatry.demo.pojo.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * Created by yeachiu on 2019/6/10.
 */
@Data
public class SysUser implements Serializable  {

    private static final long serialVersionUID = 1L;
    
    private String id;
    private String username;
    private String password;
    private Integer status;
    private Integer age;
    private String avatar;
    private Date createDate;

//////////////////////////////////////////////////////////////

    private List<cn.chiu.haveatry.demo.pojo.system.SysRole> roles;
    private Integer authStatus;

}
