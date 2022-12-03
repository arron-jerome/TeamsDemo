package com.disney.teams.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/gray")
@Slf4j
public class GrayController {

    @Resource
    IGrayService grayService;

    @GetMapping("/echo")
    public String echo() {
        return grayService.echo();
    }

    @GetMapping("/delay")
    public String delay() {
        return grayService.delay();
    }
}
