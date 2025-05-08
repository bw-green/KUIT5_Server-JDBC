package jwp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import jwp.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO users VALUES(?, ?, ?, ?)";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
        };
        jdbcTemplate.update(sql,preparedStatementSetter);
    }


    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate =new JdbcTemplate();
        String sql = "SELECT * FROM users WHERE userId = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1,userId);
        };
        RowMapper rowMapper = resultSet -> {
            return new User(resultSet.getString("userId"),resultSet.getString("password"),resultSet.getString("name"),resultSet.getString("email"));
        };
        return (User)jdbcTemplate.queryForObject(sql,preparedStatementSetter,rowMapper);
    }

    public void update(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE users SET password = ?,name = ?,email = ? WHERE userId = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1,user.getPassword());
            preparedStatement.setString(2,user.getName());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setString(4,user.getUserId());
        };
        jdbcTemplate.update(sql,preparedStatementSetter);
    }

    public List<User> findAll() throws SQLException {

        JdbcTemplate selectJdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {

        };
        String sql = "SELECT * FROM users";
        RowMapper rowMapper= resultSet -> {
            String userId = resultSet.getString("userId");
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            return new User(userId, password, name, email);
        };
        return (List<User>) selectJdbcTemplate.query(sql,preparedStatementSetter,rowMapper);

    }

}