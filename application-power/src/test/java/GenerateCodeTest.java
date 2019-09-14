import com.power.generator.builder.CodeWriter;

/**
 * @author sunyu on 2016/12/6.
 */
public class GenerateCodeTest {
    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();

        new CodeWriter().executeSpringBoot();

        long engTime = System.currentTimeMillis();

        System.out.println("costTime:" + (engTime - startTime));
    }
}
