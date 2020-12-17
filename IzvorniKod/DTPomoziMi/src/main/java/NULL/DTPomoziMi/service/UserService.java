package NULL.DTPomoziMi.service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.web.DTO.RatingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.web.DTO.UserDTO;

import java.util.Set;

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

    Set<Rating> findRatings(long id);

    Rating createRating(RatingDTO ratingDTO);
}
