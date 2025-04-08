package org.example.client2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : org.example.client2.dto.response
 * fileName       : AppResponse
 * author         : parkminsu
 * date           : 25. 4. 7.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 7.        parkminsu       최초 생성
 */
@Getter
@AllArgsConstructor
public class AppResponse {
    private String appName;
    private String appVersion;
    private String message;
    private String activeMessage;
}
