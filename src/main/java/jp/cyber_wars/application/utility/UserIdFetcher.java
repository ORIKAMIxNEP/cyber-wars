package jp.cyber_wars.application.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class UserIdFetcher {
  // ユーザーID取得
  public int fetch(final HttpServletRequest httpServletRequest) {
    return (int) httpServletRequest.getSession(false).getAttribute("sessionId");
  }
}
