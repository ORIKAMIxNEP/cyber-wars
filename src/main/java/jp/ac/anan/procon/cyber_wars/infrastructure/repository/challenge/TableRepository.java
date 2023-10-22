package jp.ac.anan.procon.cyber_wars.infrastructure.repository.challenge;

import jp.ac.anan.procon.cyber_wars.infrastructure.mapper.challenge.TableMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TableRepository {
  private final TableMapper usersTableMapper;

  // レコード取得
  public Object fetchRecord(final String targetTable, final String key) {
    return usersTableMapper.fetchRecord(targetTable, key);
  }

  // テーブル作成
  public void create(final String originalTargetTable, final String targetTable) {
    usersTableMapper.create(originalTargetTable, targetTable);
  }

  // キー挿入
  public void updateKey(final String targetTable, final String key, final String mainId) {
    usersTableMapper.updateKey(targetTable, key, mainId);
  }

  // テーブル削除
  public void drop(final String targetTable) {
    usersTableMapper.drop(targetTable);
  }
}
