package com.disney.teamsDemo.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserDto implements Serializable {
    private static final long serialVersionUID = -9080668831240862211L;

        private String userId;

    private String userName;

    private Integer age;

    @JSONField(serialize = false)
    private String desc;
}
