package com.disney.teams.service.impl;

import com.disney.teams.common.utils.CollectionUtils;
import com.disney.teams.model.criteria.GenericCriteria;
import com.disney.teams.model.dto.UserDto;
import com.disney.teams.common.exception.DemoException;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;

@DubboService(version = "1.0.1", group = "address", timeout = 5000)
public class AddressServiceEx extends AddressService {

    @Override
    public List<UserDto> listUsers() throws DemoException {
        List<UserDto> userDtoList = super.listUsers();
        if (CollectionUtils.isEmpty(userDtoList)) {
            return new ArrayList<>();
        }
        return userDtoList;
    }
}
