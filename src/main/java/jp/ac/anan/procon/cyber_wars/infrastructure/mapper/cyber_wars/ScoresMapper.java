package jp.ac.anan.procon.cyber_wars.infrastructure.mapper.cyber_wars;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ScoresMapper {
  // ヒントスコア取得
  @Select(
      """
      SELECT
        score
      FROM
        scores
      WHERE
        game_id = 2
      """)
  short fetchHintScore();
}
