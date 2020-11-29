package cn.chiu.haveatry.demo.pojo.system;


import cn.chiu.haveatry.demo.pojo.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * Created by yeachiu on 2019/6/10.
 */
@Data
public class SysDictionary extends Schema implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String parentId;
	
	private String dictCode;
	
	private String dictName;
	
	private String dictValue;
	
	private Integer sort;
	
	private String remark;
	
	//////////////////////////////////////////////////////////////////

	private List<SysDictionary> children;

	private SysDictionary parent;

}
