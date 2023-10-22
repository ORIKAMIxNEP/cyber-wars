package jp.ac.anan.procon.cyber_wars.infrastructure.repository.cyber_wars;

import jp.ac.anan.procon.cyber_wars.domain.entity.Games;
import jp.ac.anan.procon.cyber_wars.infrastructure.mapper.cyber_wars.GamesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GamesRepository {
  private final GamesMapper gamesMapper;

  // ゲーム追加
  public void addGame(
      final int userId, final int roomId, final int challengeId, final byte gameId) {
    gamesMapper.addGame(userId, roomId, challengeId, gameId);
  }

  // スコア追加
  public void addScore(
      final int userId,
      final int challengeId,
      final int roomId,
      final byte gameId,
      final short scoreMultiplier) {
    gamesMapper.addScore(userId, challengeId, roomId, gameId, scoreMultiplier);
  }

  // ゲーム取得
  public Games fetchGame(
      final int userId, final int roomId, final int challengeId, final byte gameId) {
    return gamesMapper.fetchGame(userId, roomId, challengeId, gameId);
  }

  // 課題ID取得
  public int[] fetchChallengeIds(final int roomId) {
    return gamesMapper.fetchChallengeIds(roomId);
  }

  // スコア取得
  public short fetchScore(
      final int userId, final int roomId, final int challengeId, final byte gameId) {
    return gamesMapper.fetchScore(userId, roomId, challengeId, gameId);
  }

  // 合計スコア取得
  public short fetchTotalScore(
      final int userId, final int roomId, final int challengeId, final boolean self) {
    return gamesMapper.fetchTotalScore(userId, roomId, challengeId, self);
  }
}
