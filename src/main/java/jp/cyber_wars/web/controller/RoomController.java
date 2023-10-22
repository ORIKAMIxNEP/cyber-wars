package jp.cyber_wars.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jp.cyber_wars.application.service.RoomService;
import jp.cyber_wars.application.utility.HttpClientErrorHandler;
import jp.cyber_wars.domain.dto.room.CreateRequest;
import jp.cyber_wars.domain.dto.room.JoinRequest;
import jp.cyber_wars.domain.dto.room.UpdateTimeLimitRequest;
import jp.cyber_wars.domain.dto.utility.HttpClientErrorHandlerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
  private final HttpClientErrorHandler httpClientErrorHandler;
  private final RoomService roomService;

  // ルーム作成
  @PostMapping
  @ResponseBody
  public ResponseEntity<?> create(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final CreateRequest createRequest,
      final BindingResult bindingResult,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(roomService.create(createRequest, httpServletRequest));
  }

  // ルーム参加
  @PutMapping
  @ResponseBody
  public ResponseEntity<?> join(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final JoinRequest joinRequest,
      final BindingResult bindingResult,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(roomService.join(joinRequest, httpServletRequest));
  }

  // ルーム情報取得
  @GetMapping
  @ResponseBody
  public ResponseEntity<?> fetchInformation(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(roomService.fetchInformation(httpServletRequest));
  }

  // ルーム制限時間更新
  @PatchMapping
  public ResponseEntity<?> updateTimeLimit(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final UpdateTimeLimitRequest updateTimeLimitRequest,
      final BindingResult bindingResult,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    roomService.updateTimeLimit(updateTimeLimitRequest, httpServletRequest);
    return ResponseEntity.ok().build();
  }

  // ルーム退出
  @DeleteMapping
  public ResponseEntity<?> exit(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    roomService.exit(httpServletRequest);
    return ResponseEntity.ok().build();
  }
}
