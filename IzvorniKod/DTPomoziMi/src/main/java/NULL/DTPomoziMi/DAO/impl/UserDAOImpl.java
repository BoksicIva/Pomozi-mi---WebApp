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
        String sql = "INSERT into KORISNIK (IME, PREZIME, LOZINKA, EMAIL, ULOGA, AKTIVAN, TOKEN, DULJINA, SIRINA) values (?,?,?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                sql, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN, Types.VARCHAR, Types.NUMERIC, Types.NUMERIC);

        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().toString(),
                user.isEnabled(),
                user.getToken(),
                user.getLongitude(),
                user.getLatitude()
        ));

        jdbcTemplate.update(psc, keyHolder);

        user.setId(keyHolder.getKey().longValue());

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM KORISNIK WHERE EMAIL = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new User(
                            rs.getLong("ID_KORISNIK"),
                            rs.getString("IME"),
                            rs.getString("PREZIME"),
                            rs.getString("LOZINKA"),
                            rs.getString("EMAIL"),
                            Role.valueOf(rs.getString("ULOGA")),
                            rs.getBoolean("AKTIVAN"),
                            rs.getString("TOKEN"),
                            rs.getBigDecimal("DULJINA"),
                            rs.getBigDecimal("SIRINA")
                    );
                }
            });
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
