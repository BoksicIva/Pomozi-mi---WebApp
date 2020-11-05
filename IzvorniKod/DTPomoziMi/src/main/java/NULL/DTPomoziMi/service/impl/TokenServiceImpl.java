package NULL.DTPomoziMi.service.impl;

import NULL.DTPomoziMi.DAO.TokenDAO;
import NULL.DTPomoziMi.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenDAO tokenDAO;

    @Override
    public void updateToken(String token, String email) {
        email = email == null ? null : email.trim();

        tokenDAO.updateToken(token, email);
    }

    @Override
    public String getTokenByEmail(String email) {
        email = email == null ? null : email.trim();

        return tokenDAO.getTokenByEmail(email);
    }
}
