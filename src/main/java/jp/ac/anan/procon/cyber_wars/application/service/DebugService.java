package jp.ac.anan.procon.cyber_wars.application.service;

import jp.ac.anan.procon.cyber_wars.application.utility.ArrayToStringConverter;
import jp.ac.anan.procon.cyber_wars.domain.dto.debug.EnableChallengesRequest;
import jp.ac.anan.procon.cyber_wars.infrastructure.repository.cyber_wars.ChallengesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DebugService {
  private final ChallengesRepository challengesRepository;
  private final ArrayToStringConverter arrayToStringConverter;

  // 課題有効化
  public void enableChallenges(final EnableChallengesRequest enableChallengesRequest) {
    // 有効化対象が全てである場合
    if (enableChallengesRequest.all()) {
      challengesRepository.enableAllChallenges();
    } else {
      challengesRepository.disableAllChallenges();
      challengesRepository.enableChallenges(
          arrayToStringConverter.convert(enableChallengesRequest.challengeIds()));
    }
  }
}
