package jp.ac.anan.procon.cyber_wars.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jp.ac.anan.procon.cyber_wars.application.service.GameAttackService;
import jp.ac.anan.procon.cyber_wars.application.utility.HttpClientErrorHandler;
import jp.ac.anan.procon.cyber_wars.domain.dto.game.attack.SendKeyRequest;
import jp.ac.anan.procon.cyber_wars.domain.dto.utility.HttpClientErrorHandlerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game/attack")
@RequiredArgsConstructor
public class GameAttackController {
  private final HttpClientErrorHandler httpClientErrorHandler;
  private final GameAttackService gameAttackService;

  // アタックフェーズ：課題取得
  @GetMapping("/challenge")
  @ResponseBody
  public ResponseEntity<?> fetchChallenge(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(gameAttackService.fetchChallenge(httpServletRequest));
  }

  // アタックフェーズ：ヒント使用
  @PostMapping("/hint")
  public ResponseEntity<?> useHint(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    gameAttackService.useHint(httpServletRequest);
    return ResponseEntity.ok().build();
  }

  // アタックフェーズ：キー送信
  @PostMapping("/key")
  @ResponseBody
  public ResponseEntity<?> sendKey(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final SendKeyRequest sendKeyRequest,
      final BindingResult bindingResult,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(gameAttackService.sendKey(sendKeyRequest, httpServletRequest));
  }
}
