import com.power.common.util.StringUtil;

/**
 *
 */
public class TemplateTest {

    public static void main(String[] args) throws Exception {
        String app = "smart-ctcc";
        System.out.println(StringUtil.firstToUpperCase(StringUtil.hyphenLineToCamel(app)));
//        Map<String,String> map = BeetlTemplateUtil.getTemplatesRendered("template/assembly/bin",null);
//        for(Map.Entry<String,String> entry:map.entrySet()){
//            System.out.println(entry.getKey());
//        }


    }
}
