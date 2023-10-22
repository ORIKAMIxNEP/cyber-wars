package jp.ac.anan.procon.cyber_wars.application.utility;

import org.springframework.stereotype.Component;

@Component
public class ArrayToStringConverter {
  // 文字列フォーマット
  public String convert(final int[] array) {
    final StringBuilder string = new StringBuilder();

    for (final int element : array) {
      string.append(",").append(element);
    }

    return string.substring(1);
  }
}
