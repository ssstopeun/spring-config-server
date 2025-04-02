package org.example.client2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
