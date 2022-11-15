package com.capco.homework.featureflagmanager.api.business;

import com.capco.homework.featureflagmanager.api.business.model.BusinessDataDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

// usually I have API in a separate module,
// so I can import it to client microservice instead of generating code from Swagger.
public interface BusinessApi {
    @ApiOperation(value = "Returns 'Hello World!' - or somenthing more interesting if feature FANCY_GREETING is active.")
    @GetMapping("/api/business/greetings")
    BusinessDataDto getGreetings();

    @ApiOperation(value = "Works only if feature ELABORATED_REPORTS is active.")
    @GetMapping("/api/business/reports")
    BusinessDataDto getReports();
}
