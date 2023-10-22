package jp.cyber_wars.domain.dto.game.battle;

public record SendKeyResponse(Boolean valid, boolean correct, Short score) {}
