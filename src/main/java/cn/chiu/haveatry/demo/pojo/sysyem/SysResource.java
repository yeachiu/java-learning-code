package cn.chiu.haveatry.demo.pojo.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * Created by yeachiu on 2019/6/10.
 */

@Data
public class SysResource implements Serializable {

    private String id;

    private String name;

    private String parentId;

    private Short type;

    private String url;

    private String permission;

    private String color;

    private String icon;

    private Long sort;

    private Boolean verification;

    private Date createDate;

    ///////////////////////////////////////////////////////////////////

    private List<SysResource> children;

}
