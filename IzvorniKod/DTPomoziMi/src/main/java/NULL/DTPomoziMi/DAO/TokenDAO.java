package NULL.DTPomoziMi.DAO;

public interface TokenDAO {

    void updateToken(String token, String email);
    String getTokenByEmail(String email);

}
