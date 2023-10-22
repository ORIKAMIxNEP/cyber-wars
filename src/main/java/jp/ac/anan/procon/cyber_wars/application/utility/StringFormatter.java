package jp.ac.anan.procon.cyber_wars.application.utility;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;

@Component
public class StringFormatter {
  // 文字列フォーマット
  public String format(final String unformattedString) {
    return StringEscapeUtils.escapeEcmaScript(StringEscapeUtils.escapeHtml4(unformattedString));
  }
}
