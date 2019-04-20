mybatis-template主要作用是重写了mybatis的SqlSessionTemplate，
[代码源出处](https://github.com/igool/spring-jta-mybatis) 非常感谢源作者开放的源代码。目前网络上多数
基于分布式事务的动态数据源切或多或少都有问题，该解决方法目前测试有效。
```
<!-- https://mvnrepository.com/artifact/com.github.shalousun/mybatis-template -->
<dependency>
    <groupId>com.github.shalousun</groupId>
    <artifactId>mybatis-template</artifactId>
    <version>1.0</version>
</dependency>

```
mybatis-template模块依赖于datasource-aspect模块，因此使用过程中需要将datasource-aspect安装或上传到自己的私服

# springboot下集成mybatis-template实现多数据源切换和分布式事务管理

```
/**
 * 针对druid数据源配置
 *
 * @author ${authorName} on ${createTime}.
 */
public abstract class AbstractDataSourceConfig {

    protected DataSource getDataSource(Environment env,String prefix,String dataSourceName){
        Properties prop = build(env,prefix);
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName(dataSourceName);
        ds.setXaProperties(prop);
        return ds;
    }

    protected Properties build(Environment env, String prefix) {
        Properties prop = new Properties();
        prop.put("url", env.getProperty(prefix + "url"));
        prop.put("username", env.getProperty(prefix + "username"));
        prop.put("password", env.getProperty(prefix + "password"));
        prop.put("driverClassName", env.getProperty(prefix + "driver-class-name", ""));
        prop.put("initialSize", env.getProperty(prefix + "initialSize", Integer.class));
        prop.put("maxActive", env.getProperty(prefix + "maxActive", Integer.class));
        prop.put("minIdle", env.getProperty(prefix + "minIdle", Integer.class));
        prop.put("maxWait", env.getProperty(prefix + "maxWait", Integer.class));
        prop.put("poolPreparedStatements", env.getProperty(prefix + "poolPreparedStatements", Boolean.class));
        prop.put("maxPoolPreparedStatementPerConnectionSize",env.getProperty(prefix + "maxPoolPreparedStatementPerConnectionSize", Integer.class));
        prop.put("validationQuery", env.getProperty(prefix + "validationQuery"));
        prop.put("validationQueryTimeout", env.getProperty(prefix + "validationQueryTimeout", Integer.class));
        prop.put("testOnBorrow", env.getProperty(prefix + "testOnBorrow", Boolean.class));
        prop.put("testOnReturn", env.getProperty(prefix + "testOnReturn", Boolean.class));
        prop.put("testWhileIdle", env.getProperty(prefix + "testWhileIdle", Boolean.class));
        prop.put("timeBetweenEvictionRunsMillis", env.getProperty(prefix + "timeBetweenEvictionRunsMillis", Integer.class));
        prop.put("minEvictableIdleTimeMillis", env.getProperty(prefix + "minEvictableIdleTimeMillis", Integer.class));
        prop.put("useGlobalDataSourceStat",env.getProperty(prefix + "useGlobalDataSourceStat", Boolean.class))
        prop.put("filters", env.getProperty(prefix + "filters"));
        return prop;
    }
}

```
项目的多数据源配置
```
@Configuration
@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfig extends AbstractDataSourceConfig {

    static final String BASE_PACKAGE = "com.power.learn.dao";

    static final String ALIASES_PACKAGE = "com.power.learn.model";

    static final String MAPPER_LOCATION = "classpath:com/power/learn/mapping/*.xml";


    @Primary
    @Bean(name = "dataSourceOne")
    public DataSource dataSourceOne(Environment env) {
        String prefix = "spring.datasource.druid.one.";
        return getDataSource(env,prefix,"one");
    }

    @Bean(name = "dataSourceTwo")
    public DataSource dataSourceTwo(Environment env) {
        String prefix = "spring.datasource.druid.two.";
        return getDataSource(env,prefix,"two");
    }



    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier("dataSourceOne")DataSource dataSourceOne,@Qualifier("dataSourceTwo")DataSource dataSourceTwo) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("one",dataSourceOne);
        targetDataSources.put("two",dataSourceTwo);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(dataSourceOne);
        return dataSource;
    }

    @Bean(name = "sqlSessionFactoryOne")
    public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("dataSourceOne") DataSource dataSource)
        throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    @Bean(name = "sqlSessionFactoryTwo")
    public SqlSessionFactory sqlSessionFactoryTwo(@Qualifier("dataSourceTwo") DataSource dataSource)
        throws Exception {
        return createSqlSessionFactory(dataSource);
    }




    @Bean(name = "sqlSessionTemplate")
    public CustomSqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryOne")SqlSessionFactory factoryOne,@Qualifier("sqlSessionFactoryTwo")SqlSessionFactory factoryTwo) throws Exception {
        Map<Object,SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();
        sqlSessionFactoryMap.put("one",factoryOne);
        sqlSessionFactoryMap.put("two",factoryTwo);

        CustomSqlSessionTemplate customSqlSessionTemplate = new CustomSqlSessionTemplate(factoryOne);
        customSqlSessionTemplate.setTargetSqlSessionFactorys(sqlSessionFactoryMap);
        return customSqlSessionTemplate;
    }

    /**
     * 创建数据源
     * @param dataSource
     * @return
     */
    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage(ALIASES_PACKAGE);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session
                .Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }
}
```
增加分布式事务配置,分布式事务依赖于atomikos
```
 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jta-atomikos</artifactId>
</dependency>
```
事务配置代码，可以在yml中启用jta事务省略下面代码
```
@Configuration
@ComponentScan
@EnableTransactionManagement
public class TransactionManagerConfig {

	@Bean(name = "userTransaction")
	public UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(10000);
		return userTransactionImp;
	}

	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
	public TransactionManager atomikosTransactionManager() throws Throwable {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
		return userTransactionManager;
	}

	@Bean(name = "transactionManager")
	@DependsOn({ "userTransaction", "atomikosTransactionManager" })
	public PlatformTransactionManager transactionManager() throws Throwable {
		UserTransaction userTransaction = userTransaction();
		JtaTransactionManager manager = new JtaTransactionManager(userTransaction,atomikosTransactionManager());
		return manager;
	}

}

```
事务启用,当然也可以使用申明式事务
```
@Transactional
@Override
public CommonResult save(Student entity) {
    CommonResult result = new CommonResult();
    try {
        studentOneDao.save(entity);
        studentTwoDao.save(entity);
        int a = 10/0;
        result.setSuccess(true);
    } catch (Exception e) {
        result.setMessage("添加数据失败");
        logger.error("StudentService添加数据异常：",e);
        throw new RuntimeException("添加数据失败");
    }
    return result;
}
```
