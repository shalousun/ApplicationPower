import com.power.generator.database.MySqlProvider;

public class MysqlProviderTest {

    public static void main(String[] args) {
        MySqlProvider provider = new MySqlProvider();
        provider.getTableInfo("table_match_result");
    }
}
