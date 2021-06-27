package com.flinker.web.controller;

import com.flinker.web.service.MashUpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MashUpController {

    @Resource
    private MashUpService mashUpService;

    @GetMapping("/musicMashup/{mid}")
    public String musicBrainz(@PathVariable String mid) {
        return mashUpService.parseUrl(mid);
    }
}
