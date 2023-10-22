package jp.ac.anan.procon.cyber_wars.infrastructure.repository.cyber_wars;

import java.sql.Timestamp;
import java.util.List;
import jp.ac.anan.procon.cyber_wars.domain.entity.Rooms;
import jp.ac.anan.procon.cyber_wars.domain.pojo.TimeLimit;
import jp.ac.anan.procon.cyber_wars.infrastructure.mapper.cyber_wars.RoomsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomsRepository {
  private final RoomsMapper roomsMapper;

  // ルーム作成
  public void create(
      final short inviteId,
      final int challengeId,
      final short attackPhaseTimeLimit,
      final short defencePhaseTimeLimit,
      final short battlePhaseTimeLimit) {
    roomsMapper.create(
        inviteId, challengeId, attackPhaseTimeLimit, defencePhaseTimeLimit, battlePhaseTimeLimit);
  }

  // ルーム取得 by 招待ID
  public Rooms fetchRoomByInviteId(final short inviteId) {
    return roomsMapper.fetchRoomByInviteId(inviteId);
  }

  // 動作ルーム取得
  public List<Rooms> fetchActiveRooms() {
    return roomsMapper.fetchActiveRooms();
  }

  // 課題ID取得
  public int fetchChallengeId(final int roomId) {
    return roomsMapper.fetchChallengeId(roomId);
  }

  // ゲーム開始時刻取得
  public Timestamp fetchStartTime(final int roomId) {
    return roomsMapper.fetchStartTime(roomId);
  }

  // 制限時間取得
  public TimeLimit fetchTimeLimit(final int roomId) {
    return roomsMapper.fetchTimeLimit(roomId);
  }

  // スコア倍率取得
  public short fetchScoreMultiplier(final int roomId, final String phase, final short timeOffset) {
    return roomsMapper.fetchScoreMultiplier(roomId, phase, timeOffset);
  }

  // ルーム動作判定
  public boolean isActive(final int roomId) {
    return roomsMapper.isActive(roomId);
  }

  // 課題ID更新
  public void updateChallengeId(final int roomId, final int challengeId) {
    roomsMapper.updateChallengeId(roomId, challengeId);
  }

  // ゲーム開始時刻更新
  public void updateStartTime(final int roomId) {
    roomsMapper.updateStartTime(roomId);
  }

  // 制限時間更新
  public void updateTimeLimit(
      final int roomId,
      final short attackPhaseTimeLimit,
      final short defencePhaseTimeLimit,
      final short battlePhaseTimeLimit) {
    roomsMapper.updateTimeLimit(
        roomId, attackPhaseTimeLimit, defencePhaseTimeLimit, battlePhaseTimeLimit);
  }

  // ルーム開放
  public void open(final int roomId) {
    roomsMapper.open(roomId);
  }

  // ルーム閉鎖
  public void close(final int roomId) {
    roomsMapper.close(roomId);
  }
}
