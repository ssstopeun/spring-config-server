package org.example.client2.service;

import lombok.RequiredArgsConstructor;
import org.example.client2.dto.response.AppResponse;
import org.example.client2.provider.AppProvider;
import org.example.client2.provider.Client2Provider;
import org.springframework.stereotype.Service;

/**
 * packageName    : org.example.client2.service
 * fileName       : AppService
 * author         : parkminsu
 * date           : 25. 4. 7.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 7.        parkminsu       최초 생성
 */
@Service
@RequiredArgsConstructor
public class AppService {
    private final AppProvider appProvider;
    private final Client2Provider client2Provider;

    public AppResponse getAppResponse(){
        String appName = appProvider.getAppConfig().getName();
        String appVersion = appProvider.getAppConfig().getVersion();

        String message = client2Provider.getClient2Config().getMessage();
        String activeMessage = client2Provider.getClient2Config().getProfile().getActive().getMessage();

        return new AppResponse(appName, appVersion, message, activeMessage);
    }
}
