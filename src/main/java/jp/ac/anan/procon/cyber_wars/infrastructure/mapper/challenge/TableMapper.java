package jp.ac.anan.procon.cyber_wars.infrastructure.mapper.challenge;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TableMapper {
  // レコード取得
  @Select(
      """
      SELECT
        *
      FROM
        ${targetTable}
      WHERE
        name = #{key}
      """)
  Object fetchRecord(final String targetTable, final String key);

  // テーブル作成
  @Insert(
      """
      CREATE TABLE
        ${targetTable}(
          SELECT
            *
          FROM
            ${originalTargetTable}
        )
      """)
  void create(final String originalTargetTable, final String targetTable);

  // キー更新
  @Update(
      """
      UPDATE
        ${targetTable}
      SET
        name = #{key}
      WHERE
        ${mainId} = 1
      """)
  void updateKey(final String targetTable, final String key, final String mainId);

  // テーブル削除
  @Delete("""
      DROP TABLE
        ${targetTable}
      """)
  void drop(final String targetTable);
}
