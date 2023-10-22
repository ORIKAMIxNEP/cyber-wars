package jp.cyber_wars.application.utility;

import java.util.List;
import java.util.Random;
import jp.cyber_wars.domain.entity.Rooms;
import jp.cyber_wars.infrastructure.repository.cyber_wars.RoomsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RandomGenerator {
  private final RoomsRepository roomsRepository;

  // 招待ID生成
  public short generateInviteId() {
    short inviteId;
    final Random random = new Random();
    final List<Rooms> activeRooms = roomsRepository.fetchActiveRooms();

    randomNumberGenerationLoop:
    while (true) {
      inviteId = (short) (random.nextInt(9000) + 1000);
      for (final Rooms room : activeRooms) {
        // 招待IDが等しい場合
        if (inviteId == room.getInviteId()) {
          continue randomNumberGenerationLoop;
        }
      }
      break;
    }

    return inviteId;
  }

  // キー生成
  public String generateKey() {
    final StringBuilder key = new StringBuilder();
    final Random random = new Random();

    for (byte i = 0; i < 10; i++) {
      final short alphabetNumber = (short) random.nextInt(52);
      // アルファベットの数値が26未満である場合
      if (alphabetNumber < 26) {
        key.append((char) ('A' + alphabetNumber));
      } else {
        key.append((char) ('a' + alphabetNumber - 26));
      }
    }

    return key.toString();
  }
}
