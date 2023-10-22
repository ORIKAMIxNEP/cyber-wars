package jp.ac.anan.procon.cyber_wars.application.utility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionManager {
  // ユーザーログイン判定
  public boolean isLoggedIn(final HttpServletRequest httpServletRequest) {
    final HttpSession httpSession = httpServletRequest.getSession(false);

    // ユーザーセッションが存在しない場合
    if (httpSession == null) {
      return false;
    }

    updateTimeout(httpSession);

    return true;
  }

  // ユーザーセッションタイムアウト更新
  public void updateTimeout(final HttpSession httpSession) {
    httpSession.setMaxInactiveInterval(60 * 60);
  }
}
