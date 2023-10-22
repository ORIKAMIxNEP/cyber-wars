package jp.cyber_wars.application.service;

import static jp.cyber_wars.application.Constant.PHP_DIRECTORY_PATH;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import jp.cyber_wars.application.utility.KeySender;
import jp.cyber_wars.application.utility.UserIdFetcher;
import jp.cyber_wars.domain.dto.game.attack.FetchChallengeResponse;
import jp.cyber_wars.domain.dto.game.attack.SendKeyRequest;
import jp.cyber_wars.domain.dto.game.attack.SendKeyResponse;
import jp.cyber_wars.domain.dto.utility.SendResponse;
import jp.cyber_wars.domain.entity.Challenges;
import jp.cyber_wars.infrastructure.repository.challenge.TableRepository;
import jp.cyber_wars.infrastructure.repository.cyber_wars.AllocationsRepository;
import jp.cyber_wars.infrastructure.repository.cyber_wars.ChallengesRepository;
import jp.cyber_wars.infrastructure.repository.cyber_wars.GamesRepository;
import jp.cyber_wars.infrastructure.repository.cyber_wars.RoomsRepository;
import jp.cyber_wars.infrastructure.repository.cyber_wars.ScoresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GameAttackService {
  private final RoomsRepository roomsRepository;
  private final AllocationsRepository allocationsRepository;
  private final GamesRepository gamesRepository;
  private final ChallengesRepository challengesRepository;
  private final ScoresRepository scoresRepository;
  private final TableRepository tableRepository;
  private final UserIdFetcher userIdFetcher;
  private final KeySender keySender;

  // アタックフェーズ：課題取得
  public FetchChallengeResponse fetchChallenge(final HttpServletRequest httpServletRequest) {
    final int roomId = allocationsRepository.fetchRoomId(userIdFetcher.fetch(httpServletRequest));
    final int challengeId = roomsRepository.fetchChallengeId(roomId);
    String targetCode;

    try {
      targetCode =
          Files.readString(Paths.get(PHP_DIRECTORY_PATH + "game/" + roomId + "/target/index.php"));
    } catch (final Exception exception) {
      exception.printStackTrace();

      return new FetchChallengeResponse(roomId, null, null, null, null, null);
    }

    final Challenges challenges = challengesRepository.fetchChallenge(challengeId);

    return new FetchChallengeResponse(
        roomId,
        targetCode,
        challenges.getGoal(),
        challenges.getChoices().split(","),
        challenges.getHint(),
        scoresRepository.fetchHintScore());
  }

  // アタックフェーズ：ヒント使用
  public void useHint(final HttpServletRequest httpServletRequest) {
    final int userId = userIdFetcher.fetch(httpServletRequest);
    final int roomId = allocationsRepository.fetchRoomId(userId);

    gamesRepository.addScore(
        userId, roomId, roomsRepository.fetchChallengeId(roomId), (byte) 2, (short) 100);
  }

  // アタックフェーズ：キー送信
  public SendKeyResponse sendKey(
      final SendKeyRequest sendKeyRequest, final HttpServletRequest httpServletRequest) {
    final SendResponse sendResponse =
        keySender.send(sendKeyRequest.key(), "attack", httpServletRequest);

    return new SendKeyResponse(sendResponse.valid(), sendResponse.correct(), sendResponse.score());
  }
}
