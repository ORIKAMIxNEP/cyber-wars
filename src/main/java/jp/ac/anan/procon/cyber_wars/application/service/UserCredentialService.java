package jp.ac.anan.procon.cyber_wars.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jp.ac.anan.procon.cyber_wars.application.utility.PasswordEncoder;
import jp.ac.anan.procon.cyber_wars.application.utility.SessionManager;
import jp.ac.anan.procon.cyber_wars.application.utility.StringFormatter;
import jp.ac.anan.procon.cyber_wars.application.utility.UserIdFetcher;
import jp.ac.anan.procon.cyber_wars.domain.dto.user.credential.IsLoggedInResponse;
import jp.ac.anan.procon.cyber_wars.domain.dto.user.credential.LogInRequest;
import jp.ac.anan.procon.cyber_wars.domain.dto.user.credential.LogInResponse;
import jp.ac.anan.procon.cyber_wars.domain.entity.Users;
import jp.ac.anan.procon.cyber_wars.infrastructure.repository.cyber_wars.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCredentialService {
  private final UsersRepository usersRepository;
  private final UserIdFetcher userIdFetcher;
  private final SessionManager sessionManager;
  private final StringFormatter stringFormatter;
  private final PasswordEncoder passwordEncoder;

  // ユーザーログイン
  public LogInResponse logIn(
      final LogInRequest logInRequest, final HttpServletRequest httpServletRequest) {
    final Users user = usersRepository.fetchUserByName(stringFormatter.format(logInRequest.name()));

    // ユーザーが存在しない場合
    if (user == null) {
      return new LogInResponse(false);
    }

    // ユーザーパスワードが異なる場合
    if (!passwordEncoder.matches(
        stringFormatter.format(logInRequest.password()), user.getPassword())) {
      return new LogInResponse(false);
    }

    final HttpSession httpSession = httpServletRequest.getSession();
    httpSession.setAttribute("sessionId", user.getUserId());
    sessionManager.updateTimeout(httpSession);

    return new LogInResponse(true);
  }

  // ユーザーログインチェック
  public IsLoggedInResponse isLoggedIn(final HttpServletRequest httpServletRequest) {
    // ユーザーログインをしていない場合
    if (!sessionManager.isLoggedIn(httpServletRequest)) {
      return new IsLoggedInResponse(false, null);
    }

    return new IsLoggedInResponse(
        true, usersRepository.fetchUserByUserId(userIdFetcher.fetch(httpServletRequest)).getName());
  }

  // ユーザーログアウト
  public void logOut(final HttpServletRequest httpServletRequest) {
    httpServletRequest.getSession(false).invalidate();
  }
}
