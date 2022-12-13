package controller;

import com.disney.teams.dubbo.itf.IAddressService;
import com.disney.teams.model.Msg;
import com.disney.teams.model.dto.UserDto;
import com.disney.teams.service.ISentinelService;
import com.disney.teams.service.dao.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/gray")
@Slf4j
public class GrayController {

    @Resource
    ISentinelService grayService;

    @Resource
    IAddressService addressService;
    @GetMapping("/echo")
    public UserDto echo() {
        log.info("new request echo");
        return addressService.findUser("111");
    }

    @GetMapping("/delay")
    public String delay() {
        log.info("new request delay");
        return grayService.delay();
    }
}
