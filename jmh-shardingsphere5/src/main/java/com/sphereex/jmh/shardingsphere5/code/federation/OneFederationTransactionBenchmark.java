package com.sphereex.jmh.shardingsphere5.code.federation;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Slf4j
@State(Scope.Thread)
public class OneFederationTransactionBenchmark  extends BaseFederationTest {

    private PreparedStatement[] sqlStatements;

    private Connection connection;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        connection = getConnection();
        connection.setAutoCommit(false);
        
        List<String> sqls = readLines(sqlFilePath);
        sqlStatements = new PreparedStatement[sqls.size()];
        
        for (int i = 0; i < sqls.size(); i++) {
            String line = sqls.get(i);
            if (line.trim().isEmpty()) {
                continue;
            }
            sqlStatements[i] = connection.prepareStatement(line);
        }
        connection.commit();
        connection.setAutoCommit(true);
    }

    public static List<String> readLines(final String path) throws IOException {
        return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
    }

    @Benchmark
    public void sqlAllInTransaction() throws Exception {
        try {
            connection.setAutoCommit(false);
            for (PreparedStatement sqlStatement : sqlStatements) {
                ResultSet resultSet = sqlStatement.executeQuery();
                while (resultSet.next()) {
                    resultSet.getString(1);
                }
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TearDown(Level.Trial)
    public void tearDown() throws Exception {
        for (PreparedStatement each : sqlStatements) {
            each.close();
        }
        connection.close();
    }

    public static void main(String[] args) throws RunnerException {
        new Runner(new OptionsBuilder()
                .include(OneFederationTransactionBenchmark.class.getName())
                .threads(2)
                .forks(0)
                .build()).run();
    }
}
