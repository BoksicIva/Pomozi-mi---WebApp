package NULL.DTPomoziMi.DAO.impl;

import NULL.DTPomoziMi.DAO.TokenDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TokenDAOImpl implements TokenDAO {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void updateToken(String token, String email) {
        String sql = "UPDATE KORISNIK SET TOKEN=? WHERE EMAIL = ?";

        jdbcTemplate.update(sql, new Object[] {token, email});
    }

    @Override
    public String getTokenByEmail(String email) {
        String sql = "SELECT TOKEN FROM KORISNIK WHERE EMAIL=?";

        String token = null;

        try{
            token = jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> rs.getString("TOKEN"));
        }catch(IncorrectResultSizeDataAccessException e){
            logger.error(e.getMessage());
        }

        return token;
    }
}
