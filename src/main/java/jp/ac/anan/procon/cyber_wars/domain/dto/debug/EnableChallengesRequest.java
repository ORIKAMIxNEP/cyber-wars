package jp.ac.anan.procon.cyber_wars.domain.dto.debug;

import jakarta.validation.constraints.NotNull;

public record EnableChallengesRequest(@NotNull boolean all, int[] challengeIds) {}
