package jp.cyber_wars.infrastructure.repository.cyber_wars;

import jp.cyber_wars.infrastructure.mapper.cyber_wars.AllocationsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AllocationsRepository {
  private final AllocationsMapper allocationsMapper;

  // ルーム参加
  public void join(final int userId, final short inviteId, final boolean host) {
    allocationsMapper.join(userId, inviteId, host);
  }

  // ルームID取得
  public int fetchRoomId(final int userId) {
    return allocationsMapper.fetchRoomId(userId);
  }

  // ルームホスト判定
  public boolean isHost(final int userId) {
    return allocationsMapper.isHost(userId);
  }

  // 相手ユーザーID取得
  public int fetchOpponentUserId(final int userId, final int roomId) {
    return allocationsMapper.fetchOpponentUserId(userId, roomId);
  }

  // 相手ユーザー名取得
  public String fetchOpponentName(final int userId, final int roomId) {
    return allocationsMapper.fetchOpponentName(userId, roomId);
  }

  // ルーム退出
  public void exit(final int userId) {
    allocationsMapper.exit(userId);
  }
}
