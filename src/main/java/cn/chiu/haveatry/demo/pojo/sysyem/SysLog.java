package cn.chiu.haveatry.demo.pojo.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by yeachiu on 2019/6/10.
 */

@Data
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String uid;

    private String ip;

    private Integer ajax;

    private String uri;

    private String params;

    private String httpMethod;

    private String classMethod;

    private String actionName;

    private Date createDate;


}
