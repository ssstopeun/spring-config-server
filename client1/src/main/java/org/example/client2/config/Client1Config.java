package org.example.client2.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
@AllArgsConstructor
public class Client1Config {
    private String message;
    private Profile profile;

    @Getter
    @Setter
    public static class Profile {
        private Active active;
        @Getter
        @Setter
        public static class Active {
            private String message;
        }
    }

}
