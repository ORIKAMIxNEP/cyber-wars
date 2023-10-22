package jp.ac.anan.procon.cyber_wars.domain.dto.room;

import jp.ac.anan.procon.cyber_wars.domain.pojo.TimeLimit;

public record FetchInformationResponse(
    String opponentName, boolean host, TimeLimit timeLimit, boolean started) {}
