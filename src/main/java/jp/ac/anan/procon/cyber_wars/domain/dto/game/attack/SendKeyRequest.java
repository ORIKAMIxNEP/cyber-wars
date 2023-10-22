package jp.ac.anan.procon.cyber_wars.domain.dto.game.attack;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SendKeyRequest(@NotBlank @Size(max = 20) String key) {}
