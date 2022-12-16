package com.disney.teams.service.impl;

import com.disney.teams.common.dao.criteria.GenericCriteria;
import com.disney.teams.common.utils.ClassUtils;
import com.disney.teams.common.utils.CollectionUtils;
import com.disney.teams.model.vo.BaseStatusCode;
import com.disney.teamsDemo.model.dto.UserDto;
import com.disney.teamsDemo.model.entity.UserEntity;
import com.disney.teams.common.exception.BaseStatusCode;
import com.disney.teams.service.dao.IUserService;
import com.disney.teams.dubbo.itf.IAddressService;
import com.disney.teamsDemo.model.exception.DemoException;
import com.disney.teamsDemo.model.exception.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@DubboService(version = "1.0.0", group = "address", timeout = 5000)
@Slf4j
public class AddressService implements IAddressService {

    @Resource
    IUserService userService;

    @Override
    public UserDto findUser(String userId) throws DemoException {
        if (StringUtils.isBlank(userId)) {
            throw new DemoException(BaseStatusCode.CLIENT_EMPTY_PARAMETER_CODE);
        }
        log.info("find User id {}", userId);
        UserEntity userEntity = userService.findByPropertyAndValue(UserEntity.USER_NAME_COLUMN, userId);]
//        UserEntity userEntity = new UserEntity();
//        userEntity.setId(1L);
//        userEntity.setUserName("Lily");
//        userEntity.setUserId("012sa231441as213ase23a1kj31k");
        UserDto userDto = ClassUtils.convert(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public List<UserDto> listUsers() throws DemoException {
        GenericCriteria<UserEntity> gc = new GenericCriteria<>();
        gc.setOrderBy(UserEntity.UPDATE_TIME_PROPERTY + " DESC");
        List<UserEntity> userEntityList = userService.findAllByCriteria(gc);
        if (CollectionUtils.isEmpty(userEntityList)) {
            log.info("Get {} user list is empty");
            return null;
        }
        return CollectionUtils.convert(userEntityList, UserDto.class);
    }

    @Override
    public void addUser(UserDto userDto) throws DemoException {
        log.info("add new user:{}", userDto);
    }
}
