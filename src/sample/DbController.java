package sample;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbController {

    private Connection connect() {
        String url = "jdbc:sqlite:students.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            write_error_to_log(e.getMessage());
        }
        return conn;
    }

    public Boolean check_for_validity(int number, String surname) {
        String sql = "SELECT surname, number FROM physics";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                if (surname.equals(rs.getString("surname").toLowerCase()) && number == rs.getInt("number")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            write_error_to_log(e.getMessage());
        }
        return false;
    }

    public void update_access_data(int number, int numberOfSheets) {
        write_total(number, numberOfSheets);
        write_access(number, numberOfSheets);
    }

    public void generate_report(String[] students) {
        generate_total_report(students);
        generate_access_report(students);
    }

    private void generate_access_report(String[] students) {
        String sql = "SELECT *  FROM access";
        if (!students[0].equals("*")) {
            sql = sql.concat(" WHERE number = ").concat(students[0]);
            for (int i = 1; i < students.length; i++) {
                sql = sql.concat(" OR number = ").concat(students[i]);
            }
        }
        String filename = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date()).toString() + "_access.csv";
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, false), "cp1251")) {
            try (Connection conn = this.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String text = rs.getString("datetime").toLowerCase() + ";" + rs.getString("name").toLowerCase() + ";" + rs.getString("surname").toLowerCase() + ";" + rs.getString("patronymic").toLowerCase() + ";" + rs.getString("number").toLowerCase() + ";" + rs.getString("numberofsheets").toLowerCase() + "\n";
                    writer.write(text);
                    writer.flush();
                }
            } catch (SQLException e) {
                write_error_to_log(e.getMessage());
            }
            writer.close();
        } catch (IOException ex) {
            write_error_to_log(ex.getMessage());
        }
    }

    private void generate_total_report(String[] students) {
        String sql = "SELECT *  FROM total";
        if (!students[0].equals("*")) {
            sql = sql.concat(" WHERE number = ").concat(students[0]);
            for (int i = 1; i < students.length; i++) {
                sql = sql.concat(" OR number = ").concat(students[i]);
            }
        }
        String filename = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date()).toString() + "_total.csv";
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename, false), "cp1251")) {
            try (Connection conn = this.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String text = rs.getString("name").toLowerCase() + ";" + rs.getString("surname").toLowerCase() + ";" + rs.getString("patronymic").toLowerCase() + ";" + rs.getString("number").toLowerCase() + ";" + rs.getString("totalsheets").toLowerCase() + "\n";
                    writer.write(text);
                    writer.flush();
                }
            } catch (SQLException e) {
                write_error_to_log(e.getMessage());
            }
            writer.close();
        } catch (IOException ex) {
            write_error_to_log(ex.getMessage());
        }
    }

    private void write_total(int number, int numberOfSheets) {
        String sql = "SELECT number, totalsheets  FROM total WHERE number = " + number;
        int total_sheets = 0;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            boolean check = false;
            while (rs.next()) {
                if (number == rs.getInt("number")) {
                    check = true;
                    total_sheets = rs.getInt("totalsheets");
                }
            }
            if (check) {
                total_sheets = total_sheets + numberOfSheets;
                sql = "UPDATE total SET totalsheets = " + total_sheets + " WHERE number = " + number;
                try {
                    stmt.execute(sql);
                } catch (SQLException e) {
                    write_error_to_log(e.getMessage());
                }
            } else {
                sql = "SELECT * FROM physics WHERE number = " + number;
                try {
                    ResultSet rst = stmt.executeQuery(sql);
                    sql = "INSERT INTO total VALUES ('" + rst.getString("name").toLowerCase() + "','" + rst.getString("surname").toLowerCase() + "','" + rst.getString("patronymic").toLowerCase() + "'," + rst.getString("number").toLowerCase() + "," + numberOfSheets + ")";
                    try {
                        stmt.execute(sql);
                    } catch (SQLException e) {
                        write_error_to_log(e.getMessage());
                    }
                } catch (SQLException e) {
                    write_error_to_log(e.getMessage());
                }
            }
        } catch (SQLException e) {
            write_error_to_log(e.getMessage());
        }
    }

    private void write_access(int number, int numberOfSheets) {
        String sql = "SELECT * FROM physics WHERE number = " + number;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (number == rs.getInt("number")) {
                    sql = "INSERT INTO access (datetime,name,surname,patronymic,number,numberofsheets) VALUES ('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "','" + rs.getString("name").toLowerCase() + "','" + rs.getString("surname").toLowerCase() + "','" + rs.getString("patronymic").toLowerCase() + "'," + rs.getString("number").toLowerCase() + "," + numberOfSheets + ")";
                    try {
                        stmt.execute(sql);
                    } catch (SQLException e) {
                        write_error_to_log(e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            write_error_to_log(e.getMessage());
        }
    }

    private void write_error_to_log(String error) {
        try (FileWriter writer = new FileWriter("errorlog.txt", false)) {
            writer.write(error);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
