package cn.chiu.haveatry.demo.pojo.system;

import lombok.Data;

import java.io.Serializable;
/**
 * Created by yeachiu on 2019/6/10.
 */

@Data
public class SysUserRole implements Serializable {

    private String id;

    private String uid;

    private String rid;

}
