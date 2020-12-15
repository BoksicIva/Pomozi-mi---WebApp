package NULL.DTPomoziMi.service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.web.DTO.UserDTO;

public interface UserService {

    User registerUser(UserDTO user);

    User getUserByEmail(String email);

    /**
     *
     * @param id
     * @return
     * @throws EntityMissingException
     */
    User fetch(long id);

    /**
     * @throws EntityMissingException
     */
    User getUserByID(long ID);

    Page<User> findUsers(Pageable pageable); // TODO prosiri parametre svime sta zelis... a ne to u controlleru radit

}
