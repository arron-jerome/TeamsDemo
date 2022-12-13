package com.disney.teams.model.entity;


import com.disney.teams.common.dao.entity.MysqlEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = UserEntity.TABLE_NAME)
public class UserEntity extends MysqlEntity {
    public static final String TABLE_NAME = "user";

    @Column(name = USER_NAME_COLUMN, nullable = false)
    private String userName;
    public static final String USER_NAME_PROPERTY = "userName";
    public static final String USER_NAME_COLUMN = "user_name";


    @Column(name = USER_ID_COLUMN, nullable = false)
    private String userId;
    public static final String USER_ID_PROPERTY = "userId";
    public static final String USER_ID_COLUMN = "user_id";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
