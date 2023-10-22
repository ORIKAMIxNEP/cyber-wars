package jp.cyber_wars.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jp.cyber_wars.application.service.UserCredentialService;
import jp.cyber_wars.application.utility.HttpClientErrorHandler;
import jp.cyber_wars.domain.dto.user.credential.LogInRequest;
import jp.cyber_wars.domain.dto.utility.HttpClientErrorHandlerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/credential")
@RequiredArgsConstructor
public class UserCredentialController {
  private final HttpClientErrorHandler httpClientErrorHandler;
  private final UserCredentialService userCredentialService;

  // ユーザーログイン
  @PostMapping
  @ResponseBody
  public ResponseEntity<?> logIn(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final LogInRequest logInRequest,
      final BindingResult bindingResult,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult, null);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(userCredentialService.logIn(logInRequest, httpServletRequest));
  }

  // ユーザーログインチェック
  @GetMapping
  @ResponseBody
  public ResponseEntity<?> isLoggedIn(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, null);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(userCredentialService.isLoggedIn(httpServletRequest));
  }

  // ユーザーログアウト
  @DeleteMapping
  public ResponseEntity<?> logOut(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      final HttpServletRequest httpServletRequest) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null, httpServletRequest);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    userCredentialService.logOut(httpServletRequest);
    return ResponseEntity.ok().build();
  }
}
