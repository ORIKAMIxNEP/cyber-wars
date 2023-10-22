package jp.ac.anan.procon.cyber_wars.domain.dto.game.attack;

public record FetchChallengeResponse(
    int targetPath, String code, String goal, String[] choices, String hint, Short hintScore) {}
