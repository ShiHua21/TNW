package com.jic.tnw.web.api.controller.v2;


import com.jic.elearning.web.api.config.LocaleMessageSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "V2-Test", tags = {"Z-V2"})
@RequestMapping("/v2")
public class V2TestController {

    @Autowired
    private LocaleMessageSourceService localeMessageSourceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "test", notes = "v2")
    public ResponseEntity<?> index() throws Exception {
        return ResponseEntity.ok("v2");
    }
}