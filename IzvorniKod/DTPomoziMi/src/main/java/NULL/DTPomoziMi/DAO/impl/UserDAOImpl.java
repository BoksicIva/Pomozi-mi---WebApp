package NULL.DTPomoziMi.DAO.impl;

import NULL.DTPomoziMi.DAO.UserDAO;
import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User saveUser(User user) {
        String sql = "INSERT into appUser (firstName, lastName, email, password, role, enabled) values (?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                sql, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN);

        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().toString(),
                true
        ));

        jdbcTemplate.update(psc, keyHolder);

        user.setId(keyHolder.getKey().longValue());

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM appUser WHERE email = ?";

        email = email == null ? null : email.trim();

        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();

                    user.setId(rs.getLong("id"));
                    user.setFirstName(rs.getString("lastName"));
                    user.setLastName(rs.getString("firstName"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setEnabled(true); // TODO
                    user.setRole(Role.valueOf(rs.getString("role")));

                    return user;
                }
            });

            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
