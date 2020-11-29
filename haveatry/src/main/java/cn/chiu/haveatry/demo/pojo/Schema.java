package cn.chiu.haveatry.demo.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Schema implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Date createTime;

    private String createUser;

    private Date modifyTime;

    private String modifyUser;
}
