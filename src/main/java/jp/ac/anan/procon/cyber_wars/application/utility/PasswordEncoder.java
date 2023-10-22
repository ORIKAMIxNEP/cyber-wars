package jp.ac.anan.procon.cyber_wars.application.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoder {
  private final BCryptPasswordEncoder bcryptPasswordEncoder;

  // パスワードハッシュ
  public String hash(final String unhashedPassword) {
    return bcryptPasswordEncoder.encode(unhashedPassword);
  }

  // パスワードマッチ
  public boolean matches(final String requestPassword, final String databasePassword) {
    return bcryptPasswordEncoder.matches(requestPassword, databasePassword);
  }
}
