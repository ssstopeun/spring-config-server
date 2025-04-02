package org.example.configserver;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private static final String CONFIG_REPO_PATH = "/path/to/local/repo";  // Git 저장소 로컬 경로
    private static final String CLIENT_REFRESH_URL = "http://localhost:8080/actuator/refresh";  // Config Client URL

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/git")
    @Async
    public ResponseEntity<String> handleWebhook(@RequestBody String payload) {
        try {
            System.out.println("🚀 Webhook 요청 감지! 변경 사항 확인 중...");

            // Git 변경 사항 가져오기
            Process fetchProcess = Runtime.getRuntime().exec("git -C " + CONFIG_REPO_PATH + " fetch");
            fetchProcess.waitFor();

            Process diffProcess = Runtime.getRuntime().exec("git -C " + CONFIG_REPO_PATH + " diff --name-only origin/main");
            diffProcess.waitFor();

            // 변경된 파일 목록 가져오기
            List<String> changedFiles = new String(diffProcess.getInputStream().readAllBytes())
                    .lines().collect(Collectors.toList());

            if (!changedFiles.isEmpty()) {  // 변경 사항이 있으면 실행
                System.out.println("🔄 변경 사항 감지! Config Client에게 Refresh 요청");

                // 클라이언트에 refresh 요청 전송
                restTemplate.postForObject(CLIENT_REFRESH_URL, null, String.class);
                System.out.println("✅ Config Client refresh 요청 전송 완료!");

                // 변경 사항 적용 후 pull
                Runtime.getRuntime().exec("git -C " + CONFIG_REPO_PATH + " pull").waitFor();
            } else {
                System.out.println("✅ 변경 사항 없음! refresh 요청을 보내지 않음.");
            }
            return ResponseEntity.ok("Webhook Processed");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing webhook");
        }
    }
}
