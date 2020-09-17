package com.thoughtworks.rslist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class RsListApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(RsListApplication.class, args);

        // successful connection
        // Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rsSystem","root","admin123");
        //
        // Statement statement = connection.createStatement();
        // String query = "CREATE TABLE `sys_config` (\n" +
        //         "  `variable` varchar(128) NOT NULL,\n" +
        //         "  `value` varchar(128) DEFAULT NULL,\n" +
        //         "  `set_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
        //         "  `set_by` varchar(128) DEFAULT NULL,\n" +
        //         "  PRIMARY KEY (`variable`)\n" +
        //         ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";
        // statement.execute(query);
        // connection.close();
    }

}
