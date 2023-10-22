package jp.cyber_wars.domain.dto.game.attack;

public record SendKeyResponse(Boolean valid, boolean correct, Short score) {}
