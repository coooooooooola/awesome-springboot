package com.zeburan.springboot.utils.constants;

/**
 * The enum Jdbc driver enum.
 *
 * @author  zhuzh
 */
public enum JdbcDriverEnum {
    /**
     * Mysql jdbc driver enum.
     * mysql 8.0以后建议用这个
     */
    MYSQL(1,"com.mysql.cj.jdbc.Driver"),
    /**
     * Oracle jdbc driver enum.
     */
    ORACLE(2,"oracle.jdbc.driver.OracleDriver"),
    /**
     * Sqlserver jdbc driver enum.
     */
    SQLSERVER(3,"com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    /**
     * Postgresql jdbc driver enum.
     */
    POSTGRESQL(4,"org.postgresql.Driver"),

    CLICKHOUSE(5,"ru.yandex.clickhouse.ClickHouseDriver");

    /**
     * The Index.
     */
    public int index;
    /**
     * The Driver.
     */
    public String driver;

    JdbcDriverEnum(int index, String driver) {
        this.index = index;
        this.driver = driver;
    }
}
