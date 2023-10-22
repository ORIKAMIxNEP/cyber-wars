package jp.ac.anan.procon.cyber_wars.application.utility;

import jakarta.servlet.http.HttpServletRequest;
import jp.ac.anan.procon.cyber_wars.domain.dto.utility.HttpClientErrorHandlerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class HttpClientErrorHandler {
  private final SessionManager sessionManager;

  @Value("${csrf.token}")
  private String csrfToken;

  // HTTPクライアントエラー処理
  public HttpClientErrorHandlerResponse handle(
      final String clientCsrfToken,
      final BindingResult bindingResult,
      final HttpServletRequest httpServletRequest) {

    // CSRFトークンが等しい場合
    if (!csrfToken.equals(clientCsrfToken)) {
      return new HttpClientErrorHandlerResponse(
          true, ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid CSRF Token Error"));
    }

    // バリデーションエラーがあった場合
    if (bindingResult != null && bindingResult.hasErrors()) {
      return new HttpClientErrorHandlerResponse(
          true, ResponseEntity.badRequest().body(bindingResult.getAllErrors()));
    }

    // ユーザーログインをしていない場合
    if (httpServletRequest != null && !sessionManager.isLoggedIn(httpServletRequest)) {
      return new HttpClientErrorHandlerResponse(
          true, ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Error"));
    }

    return new HttpClientErrorHandlerResponse(false, null);
  }
}
