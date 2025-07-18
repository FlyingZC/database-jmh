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
public class FederationQueryBenchmark extends BaseFederationTest {

    private final PreparedStatement[] sqlStatements = new PreparedStatement[9];

    private Connection connection;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        connection = getConnection();
        connection.setAutoCommit(false);
        List<String> sqls = readLines(sqlFilePath);
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
    public void sql_0() throws Exception {
        try {
            ResultSet resultSet = sqlStatements[0].executeQuery();
            while (resultSet.next()) {
                resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Benchmark
    public void sql_1() throws Exception {
        try {
            ResultSet resultSet = sqlStatements[1].executeQuery();
            while (resultSet.next()) {
                resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Benchmark
    public void sql_2() throws Exception {
        try {
            ResultSet resultSet = sqlStatements[2].executeQuery();
            while (resultSet.next()) {
                resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Benchmark
    public void sql_3() throws Exception {
        try {
            ResultSet resultSet = sqlStatements[3].executeQuery();
            while (resultSet.next()) {
                resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Benchmark
    public void sql_4() throws Exception {
        try {
            ResultSet resultSet = sqlStatements[4].executeQuery();
            while (resultSet.next()) {
                resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Benchmark
    public void sql_5() throws Exception {
        try {
            ResultSet resultSet = sqlStatements[5].executeQuery();
            while (resultSet.next()) {
                resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Benchmark
    public void sql_6() throws Exception {
        try {
            ResultSet resultSet = sqlStatements[6].executeQuery();
            while (resultSet.next()) {
                resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Benchmark
    public void sql_7() throws Exception {
        try {
            ResultSet resultSet = sqlStatements[7].executeQuery();
            while (resultSet.next()) {
                resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Benchmark
    public void sql_8() throws Exception {
        try {
            ResultSet resultSet = sqlStatements[8].executeQuery();
            while (resultSet.next()) {
                resultSet.getString(1);
            }
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
                .include(FederationQueryBenchmark.class.getName())
                .threads(2)
                .forks(0)
                .build()).run();
    }
}
