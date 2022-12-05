package controller;

import com.disney.teams.service.IGrayService;
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
    IGrayService grayService;

    @GetMapping("/echo")
    public String echo() {
        log.info("new request echo");
        return grayService.echo();
    }

    @GetMapping("/delay")
    public String delay() {
        log.info("new request delay");
        return grayService.delay();
    }
}
