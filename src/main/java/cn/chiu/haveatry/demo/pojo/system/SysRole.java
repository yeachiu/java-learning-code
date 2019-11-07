package cn.chiu.haveatry.demo.pojo.system;


import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * Created by yeachiu on 2019/6/10.
 */
@Data
public class SysRole implements Serializable  {

    private static final long serialVersionUID = 1L;

    private String id;
    
    private String name;

    /////////////////////////////////////////////////////

    private List<SysResource> resources;

}
