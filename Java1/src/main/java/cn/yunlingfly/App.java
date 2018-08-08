package cn.yunlingfly;

import com.alibaba.fastjson.JSON;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App extends Thread {
    private final static String DRIVER = System.getenv("DRIVERCLASSNAME");
    private final static String URL = System.getenv("URL");
    private final static String USERNAME = System.getenv("USERNAME");
    private final static String PASSWORD = System.getenv("PASSWORD");

    public static void main(String[] args) {
        Thread app = new App();
        app.run();
    }

    private Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void run() {
        System.out.println("[INFO]try connect...");
        int i = createConnection();
        if (i <= 36) {
            System.out.println("Successful connection...");
            selectAll();
        } else {
            System.out.println("Failed to connect");
        }
    }

    // 检查是否成功连接MySQL，尝试1min，不成功则报错
    public int createConnection() {
        boolean flag = true;
        Connection connection = null;

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (flag) {
            if (i > 36) break; // 超过36次循环（3分钟）则退出程序
            System.out.println("try connect");
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                flag = false;
            } catch (Exception e) {
                i++;
                flag = true;
                try {
                    sleep(5000);    // 休息5s
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return i;
    }

    private void selectAll() {
        Connection conn = getConn();
        String sql = "SELECT id,username FROM userinfo";
        Statement st;
        List<Person> list = new ArrayList<>();
        try {
            st=conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println("============================");
            while (rs.next()) {
                Person p = new Person();
                int id = rs.getInt("id");
                String username = rs.getString("username");

                p.setId(id);
                p.setUsername(username);
                list.add(p);
            }
            System.out.println(JSON.toJSONString(list));    //输出成JSON格式
            System.out.println("============================");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
