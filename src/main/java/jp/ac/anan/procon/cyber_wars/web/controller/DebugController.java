package jp.ac.anan.procon.cyber_wars.web.controller;

import jp.ac.anan.procon.cyber_wars.application.service.DebugService;
import jp.ac.anan.procon.cyber_wars.application.utility.HttpClientErrorHandler;
import jp.ac.anan.procon.cyber_wars.domain.dto.debug.EnableChallengesRequest;
import jp.ac.anan.procon.cyber_wars.domain.dto.utility.HttpClientErrorHandlerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
@RequiredArgsConstructor
public class DebugController {
  private final HttpClientErrorHandler httpClientErrorHandler;
  private final DebugService debugService;

  // 課題有効化
  @PatchMapping("/available")
  public ResponseEntity<?> enableChallenges(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final EnableChallengesRequest enableChallengesRequest,
      final BindingResult bindingResult) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult, null);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    debugService.enableChallenges(enableChallengesRequest);
    return ResponseEntity.ok().build();
  }
}
