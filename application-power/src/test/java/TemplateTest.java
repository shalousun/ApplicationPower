import com.power.generator.utils.BeetlTemplateUtil;

import java.util.Map;

/**
 *
 */
public class TemplateTest {

    public static void main(String[] args) throws Exception {

        Map<String,String> map = BeetlTemplateUtil.getTemplatesRendered("template/assembly/bin",null);
        for(Map.Entry<String,String> entry:map.entrySet()){
            System.out.println(entry.getKey());
        }




    }
}
