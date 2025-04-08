package org.example.client2.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * packageName    : org.example.client2.config
 * fileName       : AppConfig
 * author         : parkminsu
 * date           : 25. 4. 7.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 7.        parkminsu       최초 생성
 */
@RefreshScope
@Getter
@AllArgsConstructor
@ConfigurationProperties(value = "app")
public class AppConfig {
    private String name;
    private String version;
}
