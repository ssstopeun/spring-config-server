package org.example.client2.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * packageName    : org.example.client2.config
 * fileName       : Client2
 * author         : parkminsu
 * date           : 25. 4. 8.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 8.        parkminsu       최초 생성
 */
@Getter
@RefreshScope
@ConfigurationProperties(value = "client1")
public class Client2 {
    private String message;

    private String profileActiveMessage;
}
