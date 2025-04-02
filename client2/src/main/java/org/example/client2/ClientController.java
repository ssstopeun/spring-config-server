package org.example.client2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/config")
public class ClientController {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${message}")
    private String message;

    @GetMapping
    public String getConfig() {
       return "App: "+appName + " (v" + appVersion + ")\n"+ "Message: "+message;
    }
}
