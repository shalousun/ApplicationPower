import java.util.Map;
import java.util.function.Consumer;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

/**
 * @author yu3.sun on 2022/7/31
 */
public class GrokTest {

  public static void main(String[] args) {
    String message = "2022-08-01 23:00:53.603 [http-nio-8080-exec-9] ERROR com.benchmark.springboot.controller.LogController@testLog:28 - error log";
    String reg = "/^(?<log_time>\\d{4}\\-\\d{2}\\-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d+)/";
    GrokCompiler grokCompiler = GrokCompiler.newInstance();
    Grok grok = grokCompiler.compile(reg);
    Match gm = grok.match(message);
    if (!gm.isNull()) {
      /* Get the map with matches */
      final Map<String, Object> capture = gm.capture();
      for (Map.Entry<String,Object> entry:capture.entrySet()) {
        System.out.println("key="+entry.getKey() +" value="+entry.getValue());
      }
    }
  }
}
