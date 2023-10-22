package jp.ac.anan.procon.cyber_wars.infrastructure.mapper.cyber_wars;

import java.sql.Timestamp;
import java.util.List;
import jp.ac.anan.procon.cyber_wars.domain.entity.Rooms;
import jp.ac.anan.procon.cyber_wars.domain.pojo.TimeLimit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RoomsMapper {
  // ルーム作成
  @Insert(
      """
      INSERT INTO
        rooms(
          invite_id,
          challenge_id,
          attack_phase_time_limit,
          defence_phase_time_limit,
          battle_phase_time_limit
        )
      VALUES(
        #{inviteId},
        #{challengeId},
        #{attackPhaseTimeLimit},
        #{defencePhaseTimeLimit},
        #{battlePhaseTimeLimit}
      )
      """)
  void create(
      final short inviteId,
      final int challengeId,
      final short attackPhaseTimeLimit,
      final short defencePhaseTimeLimit,
      final short battlePhaseTimeLimit);

  // ルーム取得 by 招待ID
  @Select(
      """
      SELECT
        *
      FROM
        rooms
      WHERE
        invite_id = #{inviteId}
      AND
        active = TRUE
      """)
  @Results(
      id = "Rooms",
      value = {
        @Result(column = "room_id", property = "roomId"),
        @Result(column = "invite_id", property = "inviteId"),
        @Result(column = "challenge_id", property = "challengeId"),
        @Result(column = "start_time", property = "startTime"),
        @Result(column = "attack_phase_time_limit", property = "attackPhaseTimeLimit"),
        @Result(column = "defence_phase_time_limit", property = "defencePhaseTimeLimit"),
        @Result(column = "battle_phase_time_limit", property = "battlePhaseTimeLimit"),
        @Result(column = "active", property = "active")
      })
  Rooms fetchRoomByInviteId(final short inviteId);

  // 動作ルーム取得
  @Select(
      """
      SELECT
        *
      FROM
        rooms
      WHERE
        active = TRUE
      """)
  @ResultMap("Rooms")
  List<Rooms> fetchActiveRooms();

  // 課題ID取得
  @Select(
      """
      SELECT
        challenge_id
      FROM
        rooms
      WHERE
        room_id = #{roomId}
      """)
  int fetchChallengeId(final int roomId);

  // ゲーム開始時刻取得
  @Select(
      """
      SELECT
        start_time
      FROM
        rooms
      WHERE
        room_id = #{roomId}
      """)
  Timestamp fetchStartTime(final int roomId);

  // 制限時間取得
  @Select(
      """
      SELECT
        attack_phase_time_limit, defence_phase_time_limit, battle_phase_time_limit
      FROM
        rooms
      WHERE
        room_id = #{roomId}
      """)
  TimeLimit fetchTimeLimit(final int roomId);

  // スコア倍率取得
  @Select(
      """
      SELECT
        200 - FLOOR(
          (UNIX_TIMESTAMP(CURRENT_TIMESTAMP)
          - UNIX_TIMESTAMP(start_time)
          - #{timeOffset})
          / ${phase}_phase_time_limit
          * 100
        )
      FROM
        rooms
      WHERE
        room_id = #{roomId}
      """)
  short fetchScoreMultiplier(final int roomId, final String phase, final short timeOffset);

  // ルーム動作判定
  @Select(
      """
      SELECT
        active
      FROM
        rooms
      WHERE
        room_id = #{roomId}
      """)
  boolean isActive(final int roomId);

  // 課題ID更新
  @Update(
      """
      UPDATE
        rooms
      SET
        challenge_id = #{challengeId}
      WHERE
        room_id = #{roomId}
      """)
  void updateChallengeId(final int roomId, final int challengeId);

  // ゲーム開始時刻更新
  @Update(
      """
      UPDATE
        rooms
      SET
        start_time = CURRENT_TIMESTAMP
      WHERE
        room_id = #{roomId}
      """)
  void updateStartTime(final int roomId);

  // 制限時間更新
  @Update(
      """
      UPDATE
        rooms
      SET
        attack_phase_time_limit = #{attackPhaseTimeLimit},
        defence_phase_time_limit = #{defencePhaseTimeLimit},
        battle_phase_time_limit = #{battlePhaseTimeLimit}
      WHERE
        room_id = #{roomId}
      """)
  void updateTimeLimit(
      final int roomId,
      final short attackPhaseTimeLimit,
      final short defencePhaseTimeLimit,
      final short battlePhaseTimeLimit);

  // ルーム開放
  @Update(
      """
      UPDATE
        rooms
      SET
        active = TRUE
      WHERE
        room_id = #{roomId}
      """)
  void open(final int roomId);

  // ルーム閉鎖
  @Update(
      """
      UPDATE
        rooms
      SET
        active = FALSE
      WHERE
        room_id = #{roomId}
      """)
  void close(final int roomId);
}
