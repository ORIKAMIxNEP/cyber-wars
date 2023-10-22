package jp.ac.anan.procon.cyber_wars.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import lombok.Data;

@Entity
@Data
public class Rooms {
  @Id private final int roomId;

  private final short inviteId;

  private final int challengeId;

  private final Timestamp startTime;

  private final short attackPhaseTimeLimit;

  private final short defencePhaseTimeLimit;

  private final short battlePhaseTimeLimit;

  private final boolean active;
}
