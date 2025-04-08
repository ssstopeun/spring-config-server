package org.example.client2.provider;

import lombok.RequiredArgsConstructor;
import org.example.client2.config.AppConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * packageName    : org.example.client2.provider
 * fileName       : AppProvider
 * author         : parkminsu
 * date           : 25. 4. 7.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 7.        parkminsu       최초 생성
 */
@Component
@RequiredArgsConstructor
public class AppProvider {
    private final ObjectProvider<AppConfig> objectProvider;

    public AppConfig getAppConfig() {
        return objectProvider.getObject();
    }



}
