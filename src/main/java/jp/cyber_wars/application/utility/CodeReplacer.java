package jp.cyber_wars.application.utility;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class CodeReplacer {
  public void replace(final Path filePath, final String targetCode, final String replacedCode)
      throws Exception {

    try (final Stream<String> stream = Files.lines(filePath)) {
      final List<String> fileLines = new ArrayList<>();

      stream.forEach(
          fileLine -> {
            // 行に対象コードが含まれる場合
            if (fileLine.contains(targetCode)) {
              fileLines.add(fileLine.replace(targetCode, replacedCode));
            } else {
              fileLines.add(fileLine);
            }
          });
      Files.write(filePath, fileLines, Charset.forName("MS932"));
    } catch (final Exception exception) {
      throw new Exception();
    }
  }
}
