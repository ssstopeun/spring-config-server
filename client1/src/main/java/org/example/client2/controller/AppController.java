package org.example.client2.controller;

import lombok.RequiredArgsConstructor;
import org.example.client2.response.AppResponse;
import org.example.client2.service.AppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : org.example.client2.controller
 * fileName       : AppController
 * author         : parkminsu
 * date           : 25. 4. 7.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 4. 7.        parkminsu       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class AppController {
    private final AppService appService;

    @GetMapping("/config")
    public ResponseEntity<AppResponse> getResponse(){
        return ResponseEntity.ok().body(appService.getAppResponse());
    }
}
