package jp.cyber_wars.infrastructure.repository.cyber_wars;

import jp.cyber_wars.domain.entity.Users;
import jp.cyber_wars.infrastructure.mapper.cyber_wars.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsersRepository {
  private final UsersMapper usersMapper;

  // ユーザー登録
  public void register(final String name, final String password) {
    usersMapper.register(name, password);
  }

  // ユーザー取得 by ユーザーID
  public Users fetchUserByUserId(final int userId) {
    return usersMapper.fetchUserByUserId(userId);
  }

  // ユーザー取得 by ユーザー名
  public Users fetchUserByName(final String name) {
    return usersMapper.fetchUserByName(name);
  }

  // ユーザー名更新
  public void updateName(final int userId, final String name) {
    usersMapper.updateName(userId, name);
  }

  // ユーザーパスワード更新
  public void updatePassword(final int userId, final String password) {
    usersMapper.updatePassword(userId, password);
  }
}
