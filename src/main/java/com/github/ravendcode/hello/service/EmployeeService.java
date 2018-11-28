package com.github.ravendcode.hello.service;

import com.github.ravendcode.hello.Config;
import com.github.ravendcode.hello.dao.CrudDAO;
import com.github.ravendcode.hello.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements CrudDAO<Employee, Integer> {
    private final Connection db;

    public EmployeeService(Config config) {
        this.db = config.getDb();
    }

    @Override
    public void createOne(Employee employee) {
        String sql = "INSERT INTO employees (first_name, last_name, email, salary) values (?, ?, ?, ?)";
        try (PreparedStatement pstmt = db.prepareStatement(sql)) {

            setEntity(employee, pstmt);
            if (pstmt.executeUpdate() != 1) {
                throw new IllegalArgumentException(Employee.class.getSimpleName() + "Error: createOne");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setEntity(Employee employee, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, employee.getFirstName());
        pstmt.setString(2, employee.getLastName());
        pstmt.setString(3, employee.getEmail());
        pstmt.setBigDecimal(4, employee.getSalary());
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> listOfEmployee = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, email, salary FROM employees";
        try (Statement stmt = db.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getBigDecimal("salary")
                );
                listOfEmployee.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfEmployee;
    }

    @Override
    public Employee findById(Integer id) {
        ResultSet rs = null;
        Employee employee = new Employee();
        String sql = "SELECT id, first_name, last_name, email, salary FROM employees WHERE id = ?";
        try (PreparedStatement pstmt = db.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                employee.setSalary(rs.getBigDecimal("salary"));
            } else {
                throw new IllegalArgumentException(Employee.class.getSimpleName() + "Error: findById");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return employee;
    }

    @Override
    public void updateOne(Employee employee) {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.prepareStatement(sql)) {

            setEntity(employee, pstmt);
            pstmt.setInt(5, employee.getId());
            if (pstmt.executeUpdate() != 1) {
                throw new IllegalArgumentException(Employee.class.getSimpleName() + "Error: updateOne");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOne(Employee employee) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement pstmt = db.prepareStatement(sql)) {
            pstmt.setInt(1, employee.getId());
            if (pstmt.executeUpdate() != 1) {
                throw new IllegalArgumentException(Employee.class.getSimpleName() + "Error: deleteOne");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
