package com.huitai.bpm.examples.entity;

import com.huitai.bpm.annotaion.FlwAnnotaion;
import com.huitai.bpm.manage.entity.FlwBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 流程使用样板
 * </p>
 *
 * @author PLF
 * @since 2020-12-09
 */
@FlwAnnotaion("工作流使用样板")
@ApiModel(value="HtFlwTemplate对象", description="流程使用样板")
public class HtFlwTemplate extends FlwBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称（测试用，不重要）")
    @Length(max=32, message="名称（测试用，不重要）长度不能超过32个字符")
    private String name;

    @ApiModelProperty(value = "年龄（测试用，不重要）")
    private Integer age;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "HtFlwTemplate{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
