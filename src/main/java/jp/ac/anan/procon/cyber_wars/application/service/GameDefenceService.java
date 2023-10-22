package jp.ac.anan.procon.cyber_wars.application.service;

import static jp.ac.anan.procon.cyber_wars.application.Constant.PHP_DIRECTORY_PATH;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import jp.ac.anan.procon.cyber_wars.application.utility.CodeReplacer;
import jp.ac.anan.procon.cyber_wars.application.utility.UserIdFetcher;
import jp.ac.anan.procon.cyber_wars.domain.dto.game.defence.FetchRevisionPathResponse;
import jp.ac.anan.procon.cyber_wars.domain.dto.game.defence.SendCodeRequest;
import jp.ac.anan.procon.cyber_wars.domain.dto.game.defence.SendCodeResponse;
import jp.ac.anan.procon.cyber_wars.infrastructure.repository.cyber_wars.AllocationsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GameDefenceService {
  private final AllocationsRepository allocationsRepository;
  private final UserIdFetcher userIdFetcher;
  private final CodeReplacer codeReplacer;

  // ディフェンスフェーズ：修正課題パス取得
  public FetchRevisionPathResponse fetchRevisionPath(final HttpServletRequest httpServletRequest) {
    return new FetchRevisionPathResponse(userIdFetcher.fetch(httpServletRequest));
  }

  // ディフェンスフェーズ：コード送信
  public SendCodeResponse sendCode(
      final SendCodeRequest sendCodeRequest, final HttpServletRequest httpServletRequest) {
    final int userId = userIdFetcher.fetch(httpServletRequest);
    final int roomId = allocationsRepository.fetchRoomId(userId);
    final String revisionDirectoryPath = PHP_DIRECTORY_PATH + "game/" + roomId + "/revision/";
    final String temporaryPhpPathString = "temporary" + userId + ".php";
    final Path temporaryPhpPath = Paths.get(revisionDirectoryPath + temporaryPhpPathString);

    try {
      Files.writeString(temporaryPhpPath, sendCodeRequest.code());

      codeReplacer.replace(temporaryPhpPath, "shell_exec(", "safe_shell_exec(");
    } catch (final Exception exception) {
      exception.printStackTrace();

      return new SendCodeResponse(false);
    }

    try (final CloseableHttpClient httpClient =
        HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build()) {
      // ステータスコードが200でない場合
      if (httpClient
              .execute(
                  new HttpGet(
                      "http://localhost/game/" + roomId + "/revision/" + temporaryPhpPathString))
              .getStatusLine()
              .getStatusCode()
          != 200) {
        Files.delete(temporaryPhpPath);

        return new SendCodeResponse(false);
      }
    } catch (final Exception exception) {
      exception.printStackTrace();

      return new SendCodeResponse(false);
    }

    try {
      Files.copy(
          temporaryPhpPath,
          Paths.get(revisionDirectoryPath + userId + ".php"),
          StandardCopyOption.REPLACE_EXISTING);
      Files.delete(temporaryPhpPath);
    } catch (final Exception exception) {
      exception.printStackTrace();

      return new SendCodeResponse(false);
    }

    return new SendCodeResponse(true);
  }
}
