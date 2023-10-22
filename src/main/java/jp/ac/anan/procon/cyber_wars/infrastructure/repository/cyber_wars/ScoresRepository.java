package jp.ac.anan.procon.cyber_wars.infrastructure.repository.cyber_wars;

import jp.ac.anan.procon.cyber_wars.infrastructure.mapper.cyber_wars.ScoresMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScoresRepository {
  private final ScoresMapper scoresMapper;

  // ヒントスコア取得
  public short fetchHintScore() {
    return scoresMapper.fetchHintScore();
  }
}
