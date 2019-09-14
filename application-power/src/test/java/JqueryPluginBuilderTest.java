import com.power.common.util.FileUtil;
import com.power.generator.builder.JqueryPluginBuilder;

public class JqueryPluginBuilderTest {

    public static void main(String[] args) {

        JqueryPluginBuilder builder = new JqueryPluginBuilder();
        String str = builder.writeBuilder("FormBuilder");
        // System.out.println(str);
        FileUtil.writeFileNotAppend(str, "d:\\jquery-formbuilder.js");
    }
}
