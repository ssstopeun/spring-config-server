package org.example.client2.provider;

import lombok.RequiredArgsConstructor;
import org.example.client2.config.Client2Config;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * packageName    : org.example.client2.provider
 * fileName       : Client1Provider
 * author         : parkminsu
 * date           : 25. 4. 8.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 8.        parkminsu       최초 생성
 */
@Component
@RequiredArgsConstructor
public class Client2Provider {
    private final ObjectProvider<Client2Config> client2Provider;

    public Client2Config getClient2Config() {
        return client2Provider.getObject();
    }
}
