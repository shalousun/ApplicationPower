import com.power.database.MySqlProvider;

public class MysqlProviderTest {

    public static void main(String[] args) {
        MySqlProvider provider = new MySqlProvider();
        provider.getColumnsInfo("table_match_result");
    }
}
