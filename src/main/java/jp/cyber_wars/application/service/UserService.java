package jp.cyber_wars.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jp.cyber_wars.application.utility.PasswordEncoder;
import jp.cyber_wars.application.utility.StringFormatter;
import jp.cyber_wars.application.utility.UserIdFetcher;
import jp.cyber_wars.domain.dto.user.RegisterRequest;
import jp.cyber_wars.domain.dto.user.RegisterResponse;
import jp.cyber_wars.domain.dto.user.UpdateNameRequest;
import jp.cyber_wars.domain.dto.user.UpdateNameResponse;
import jp.cyber_wars.domain.dto.user.UpdatePasswordRequest;
import jp.cyber_wars.domain.dto.user.UpdatePasswordResponse;
import jp.cyber_wars.infrastructure.repository.cyber_wars.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
  private final UsersRepository usersRepository;
  private final UserIdFetcher userIdFetcher;
  private final StringFormatter stringFormatter;
  private final PasswordEncoder passwordEncoder;

  // ユーザー登録
  public RegisterResponse register(final RegisterRequest registerRequest) {
    final String formattedName = stringFormatter.format(registerRequest.name());

    // ユーザー名が空である場合
    if (formattedName.isEmpty()) {
      return new RegisterResponse(false);
    }

    // ユーザーが存在する場合
    if (usersRepository.fetchUserByName(formattedName) != null) {
      return new RegisterResponse(false);
    }

    final String formattedPassword = stringFormatter.format(registerRequest.password());

    // ユーザーパスワードが空である場合
    if (formattedPassword.isEmpty()) {
      return new RegisterResponse(false);
    }

    usersRepository.register(formattedName, passwordEncoder.hash(formattedPassword));

    return new RegisterResponse(true);
  }

  // ユーザー名更新
  public UpdateNameResponse updateName(
      final UpdateNameRequest updateNameRequest, final HttpServletRequest httpServletRequest) {
    final String formattedName = stringFormatter.format(updateNameRequest.name());

    // ユーザー名が空である場合
    if (formattedName.isEmpty()) {
      return new UpdateNameResponse(false);
    }

    // ユーザーが存在する場合
    if (usersRepository.fetchUserByName(formattedName) != null) {
      return new UpdateNameResponse(false);
    }

    usersRepository.updateName(userIdFetcher.fetch(httpServletRequest), formattedName);

    return new UpdateNameResponse(true);
  }

  // ユーザーパスワード更新
  public UpdatePasswordResponse updatePassword(
      final UpdatePasswordRequest updatePasswordRequest,
      final HttpServletRequest httpServletRequest) {
    final String formattedPassword = stringFormatter.format(updatePasswordRequest.password());

    // ユーザーパスワードが空である場合
    if (formattedPassword.isEmpty()) {
      return new UpdatePasswordResponse(false);
    }

    usersRepository.updatePassword(
        userIdFetcher.fetch(httpServletRequest), passwordEncoder.hash(formattedPassword));

    return new UpdatePasswordResponse(true);
  }
}
