package com.shilko.ru.witcher.controller.api;

import com.shilko.ru.witcher.service.ExternalResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resource")
public class ExternalResourceController {

    @Autowired
    private ExternalResourceService externalResourceService;

    @RequestMapping(value = "/bank/redirect", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getBankRedirect() {
        return ResponseEntity.ok(externalResourceService.getBankRedirect());
    }

    @RequestMapping(value = "/analytic/redirect", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getAnalyticsRedirect() {
        return ResponseEntity.ok(externalResourceService.getAnalyticsRedirect());
    }

    @RequestMapping(value = "/analytic", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getAnalyticsResource() {
        return ResponseEntity.ok(externalResourceService.getAnalyticsResource());
    }
}
