package jp.cyber_wars.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jp.cyber_wars.application.service.GameBattleService;
import jp.cyber_wars.application.utility.HttpClientErrorHandler;
import jp.cyber_wars.domain.dto.game.battle.SendKeyRequest;
import jp.cyber_wars.domain.dto.utility.HttpClientErrorHandlerResponse;
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
@RequestMapping("/game/battle")
@RequiredArgsConstructor
public class GameBattleController {
  private final HttpClientErrorHandler httpClientErrorHandler;
  private final GameBattleService gameBattleService;

  // バトルフェーズ：修正課題取得
  @GetMapping("/revision")
  @ResponseBody
  public ResponseEntity<?> fetchRevision(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(gameBattleService.fetchRevision(httpServletRequest));
  }

  // バトルフェーズ：キー送信
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
    return ResponseEntity.ok(gameBattleService.sendKey(sendKeyRequest, httpServletRequest));
  }
}
