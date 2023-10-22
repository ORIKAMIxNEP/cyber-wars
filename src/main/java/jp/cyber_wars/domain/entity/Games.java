package jp.cyber_wars.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Games {
  @Id private final int roomId;

  @Id private final int challengeId;

  @Id private final int userId;

  @Id private final byte gameId;

  private final short scoreMultiplier;
}
