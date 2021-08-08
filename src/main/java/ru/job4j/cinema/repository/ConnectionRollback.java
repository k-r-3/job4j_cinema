package ru.job4j.cinema.repository;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionRollback {

    public static Connection create(Connection cn) throws SQLException {
        cn.setAutoCommit(false);
        return (Connection) Proxy.newProxyInstance(
                ConnectionRollback.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    Object result = null;
                    if ("close".equals(method.getName())) {
                        cn.rollback();
                        cn.close();
                    } else {
                        result = method.invoke(cn, args);
                    }
                    return result;
                }
        );
    }
}
