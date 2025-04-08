package org.example.client2.service;

import lombok.RequiredArgsConstructor;
import org.example.client2.config.AppConfig;
import org.example.client2.config.Client1Config;
import org.example.client2.response.AppResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppService {
    private final AppConfig appConfig;
    private final Client1Config client1Config;

    public AppResponse getAppResponse(){
        String appName = appConfig.getName();
        String appVersion = appConfig.getVersion();
        String message = client1Config.getMessage();
        String activeMessage = client1Config.getProfile().getActive().getMessage();

        return new AppResponse(appName, appVersion, message, activeMessage);
    }
}
