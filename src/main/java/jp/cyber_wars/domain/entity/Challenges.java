package jp.cyber_wars.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Challenges {
  @Id private final int challengeId;

  private final String goal;

  private final String choices;

  private final String hint;

  private final String explanation;

  private final String targetTable;

  private final boolean available;
}
