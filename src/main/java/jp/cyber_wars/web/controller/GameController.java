package jp.cyber_wars.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jp.cyber_wars.application.service.GameService;
import jp.cyber_wars.application.utility.HttpClientErrorHandler;
import jp.cyber_wars.domain.dto.game.EndRequest;
import jp.cyber_wars.domain.dto.utility.HttpClientErrorHandlerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
  private final HttpClientErrorHandler httpClientErrorHandler;
  private final GameService gameService;

  // ゲーム開始
  @PatchMapping
  public ResponseEntity<?> start(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    gameService.start(httpServletRequest);
    return ResponseEntity.ok().build();
  }

  // ゲーム開始時刻取得
  @GetMapping("/start-time")
  @ResponseBody
  public ResponseEntity<?> fetchStartTime(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(gameService.fetchStartTime(httpServletRequest));
  }

  // スコア取得
  @GetMapping("/scores")
  @ResponseBody
  public ResponseEntity<?> fetchScores(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(gameService.fetchScores(httpServletRequest));
  }

  // 解説取得
  @GetMapping("/explanation")
  @ResponseBody
  public ResponseEntity<?> fetchExplanation(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(gameService.fetchExplanation(httpServletRequest));
  }

  // ゲーム終了
  @DeleteMapping
  @ResponseBody
  public ResponseEntity<?> end(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final EndRequest endGameRequest,
      final BindingResult bindingResult,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(gameService.end(endGameRequest, httpServletRequest));
  }
}
