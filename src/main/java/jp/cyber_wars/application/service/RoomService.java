package jp.cyber_wars.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jp.cyber_wars.application.utility.RandomGenerator;
import jp.cyber_wars.application.utility.UserIdFetcher;
import jp.cyber_wars.domain.dto.room.CreateRequest;
import jp.cyber_wars.domain.dto.room.CreateResponse;
import jp.cyber_wars.domain.dto.room.FetchInformationResponse;
import jp.cyber_wars.domain.dto.room.JoinRequest;
import jp.cyber_wars.domain.dto.room.JoinResponse;
import jp.cyber_wars.domain.dto.room.UpdateTimeLimitRequest;
import jp.cyber_wars.domain.pojo.TimeLimit;
import jp.cyber_wars.infrastructure.repository.cyber_wars.AllocationsRepository;
import jp.cyber_wars.infrastructure.repository.cyber_wars.ChallengesRepository;
import jp.cyber_wars.infrastructure.repository.cyber_wars.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {
  private final RoomsRepository roomsRepository;
  private final AllocationsRepository allocationsRepository;
  private final ChallengesRepository challengesRepository;
  private final UserIdFetcher userIdFetcher;
  private final RandomGenerator randomGenerator;

  // ルーム作成
  public CreateResponse create(
      final CreateRequest createRequest, final HttpServletRequest httpServletRequest) {
    final short inviteId = randomGenerator.generateInviteId();
    final TimeLimit timeLimit = createRequest.timeLimit();

    roomsRepository.create(
        inviteId,
        challengesRepository.fetchAvailableChallengeId(),
        timeLimit.attackPhase(),
        timeLimit.defencePhase(),
        timeLimit.battlePhase());
    allocationsRepository.join(userIdFetcher.fetch(httpServletRequest), inviteId, true);

    return new CreateResponse(inviteId);
  }

  // ルーム参加
  public JoinResponse join(
      final JoinRequest joinRequest, final HttpServletRequest httpServletRequest) {
    final short inviteId = joinRequest.inviteId();

    // ルームが存在しない場合
    if (roomsRepository.fetchRoomByInviteId(inviteId) == null) {
      return new JoinResponse(false);
    }

    allocationsRepository.join(
        userIdFetcher.fetch(httpServletRequest), joinRequest.inviteId(), false);

    return new JoinResponse(true);
  }

  // ルーム情報取得
  public FetchInformationResponse fetchInformation(final HttpServletRequest httpServletRequest) {
    final int userId = userIdFetcher.fetch(httpServletRequest);
    final int roomId = allocationsRepository.fetchRoomId(userId);
    final String opponentName = allocationsRepository.fetchOpponentName(userId, roomId);
    final TimeLimit timeLimit = roomsRepository.fetchTimeLimit(roomId);

    // ルームが動作をしていない場合 and 相手ユーザー名が存在する場合
    if (!roomsRepository.isActive(roomId) && opponentName != null) {
      return new FetchInformationResponse(
          opponentName, allocationsRepository.isHost(userId), timeLimit, true);
    }

    return new FetchInformationResponse(
        opponentName, allocationsRepository.isHost(userId), timeLimit, false);
  }

  // ルーム制限時間更新
  public void updateTimeLimit(
      final UpdateTimeLimitRequest updateTimeLimitRequest,
      final HttpServletRequest httpServletRequest) {
    final TimeLimit timeLimit = updateTimeLimitRequest.timeLimit();

    roomsRepository.updateTimeLimit(
        allocationsRepository.fetchRoomId(userIdFetcher.fetch(httpServletRequest)),
        timeLimit.attackPhase(),
        timeLimit.defencePhase(),
        timeLimit.battlePhase());
  }

  // ルーム退出
  public void exit(final HttpServletRequest httpServletRequest) {
    final int userId = userIdFetcher.fetch(httpServletRequest);

    // ユーザーがホストである場合
    if (allocationsRepository.isHost(userId)) {
      roomsRepository.close(allocationsRepository.fetchRoomId(userId));
    }

    allocationsRepository.exit(userId);
  }
}
