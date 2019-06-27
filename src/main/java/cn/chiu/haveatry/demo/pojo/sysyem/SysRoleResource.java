package cn.chiu.haveatry.demo.pojo.system;

import lombok.Data;

import java.io.Serializable;
/**
 * Created by yeachiu on 2019/6/10.
 */

@Data
public class SysRoleResource implements Serializable {

    private String id;

    private String rid;

    private String pid;

    private static final long serialVersionUID = 1L;
}
