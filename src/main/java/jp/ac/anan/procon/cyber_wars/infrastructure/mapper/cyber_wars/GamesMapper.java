package jp.ac.anan.procon.cyber_wars.infrastructure.mapper.cyber_wars;

import jp.ac.anan.procon.cyber_wars.domain.entity.Games;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GamesMapper {
  // ゲーム追加
  @Insert(
      """
      INSERT INTO
        games(
          room_id, challenge_id, user_id, game_id
        )
      VALUES(
        #{roomId}, #{challengeId}, #{userId}, #{gameId}
      )
      """)
  void addGame(final int userId, final int roomId, final int challengeId, final byte gameId);

  // スコア追加
  @Insert(
      """
      INSERT INTO
        games(
          room_id, challenge_id, user_id, game_id, score_multiplier
        )
      VALUES(
        #{roomId}, #{challengeId}, #{userId}, #{gameId}, #{scoreMultiplier}
      )
      """)
  void addScore(
      final int userId,
      final int roomId,
      final int challengeId,
      final byte gameId,
      final short scoreMultiplier);

  // ゲーム取得
  @Select(
      """
      SELECT
        *
      FROM
        games
      WHERE
        room_id = #{roomId}
      AND
        challenge_id = #{challengeId}
      AND
        user_id = #{userId}
      AND
        game_id = #{gameId}
      """)
  @Results(
      id = "Games",
      value = {
        @Result(column = "room_id", property = "roomId"),
        @Result(column = "challenge_id", property = "challengeId"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "game_id", property = "gameId"),
        @Result(column = "score_multiplier", property = "scoreMultiplier")
      })
  Games fetchGame(final int userId, final int roomId, final int challengeId, final byte gameId);

  // 課題ID取得
  @Select(
      """
      SELECT
        challenge_id
      FROM
        games
      WHERE
        room_id = #{roomId}
      AND
        game_id = 0
      """)
  int[] fetchChallengeIds(final int roomId);

  // スコア取得
  @Select(
      """
      SELECT
        FLOOR(
          score * (score_multiplier / 100)
        )
      FROM
        games
      NATURAL JOIN
        scores
      WHERE
        room_id = #{roomId}
      AND
        challenge_id = #{challengeId}
      AND
        user_id = #{userId}
      AND
        game_id = #{gameId}
      """)
  short fetchScore(final int userId, final int roomId, final int challengeId, final byte gameId);

  // 合計スコア取得
  @Select(
      """
      SELECT
        COALESCE(
          FLOOR(
            SUM(
              score * (score_multiplier / 100)
            )
          ),
          0
        )
      FROM
        games
      NATURAL JOIN
        scores
      WHERE
        room_id = #{roomId}
      AND
        CASE
          #{self}
        WHEN TRUE THEN
          user_id = #{userId}
        ELSE
          user_id != #{userId}
        END
      """)
  short fetchTotalScore(
      final int userId, final int roomId, final int challengeId, final boolean self);
}
