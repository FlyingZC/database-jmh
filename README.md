## federation JMH 使用方式
OneFederationQueryBenchmark: 不开事务执行查询操作
OneFederationTransactionBenchmark: 开启事务，执行 sql 文件中指定的所有 sql。

命令示例：
通过 `-DconfigFilePath` 指定配置文件全路径。
```shell
# 一个方法内执行所有查询
java -classpath 'dependency/*:jmh-shardingsphere5-1.0-SNAPSHOT.jar' -DconfigFilePath=/opt/rongyao/target/classes/config.properties org.openjdk.jmh.Main "com.sphereex.jmh.shardingsphere5.code.federation.OneFederationQueryBenchmark" -f 1 -i2 -r20 -t  20 -w 60 -wf 1 -wi 1

# 一个方法内执行事务+所有查询
java -classpath 'dependency/*:jmh-shardingsphere5-1.0-SNAPSHOT.jar' -DconfigFilePath=/opt/rongyao/target/classes/config.properties org.openjdk.jmh.Main "com.sphereex.jmh.shardingsphere5.code.federation.OneFederationTransactionBenchmark" -f 1 -i1 -r20 -t  20 -w 60 -wf 1 -wi 1
```
配置文件里需要通过 adapter 指定压测 proxy 还是 jdbc。
对于 proxy 需要指定 driverClassName, userName, password 等。
对于 jdbc 需要指定 jdbcYamlPath(yaml 配置文件绝对路径)。

通过 sqlFilePath 指定要测试的 SQL 文件绝对路径。

SQL 文件里一行一条 sql，暂不支持换行和空行。
