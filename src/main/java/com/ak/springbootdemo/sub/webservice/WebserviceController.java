package com.ak.springbootdemo.sub.webservice;

import com.ak.springbootdemo.sub.data.Subsidiary;
import com.ak.springbootdemo.sub.service.SubsidiaryService;
import com.ak.springbootdemo.sub.util.SubsidiaryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

/*
 * Webservice Subsidiaries Controller
 */
@RestController
@RequestMapping("/api")
public class WebserviceController {
    private final SubsidiaryService subsidiaryService;

    public WebserviceController(SubsidiaryService subsidiaryService) {
        this.subsidiaryService = subsidiaryService;
    }

    /**
     * Get Subsidiaries
     *
     * @param innerCode subsidiary innerCode   //?innerCode=value
     * @return list of requested subsidiaries.
     */
    @GetMapping(path = "/subs")
    public List<Subsidiary> getSubsidiariesByCode(@RequestParam(value = "innerCode", required = false) String innerCode) {
        if (innerCode == null) {
            return subsidiaryService.getSubsidiaries();
        } else {
            return Collections.singletonList(subsidiaryService.getSubsidiariesByInnerCode(innerCode).orElse(null));
        }
    }

    /*
     * Add Subsidiary
     *
     * @param Subsidiary DTO
     */
    @PostMapping(path = "/subs")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSubsidiary(@RequestBody SubsidiaryDTO subsidiary) {
        this.subsidiaryService.saveSubsidiary(
                subsidiary.getInnerCode(),
                subsidiary.getAddress(),
                subsidiary.getName(),
                subsidiary.getPhoneNumber());
    }
}
