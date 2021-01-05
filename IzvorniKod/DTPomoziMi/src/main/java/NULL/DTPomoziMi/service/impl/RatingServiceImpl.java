package NULL.DTPomoziMi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.exception.IllegalAccessException;
import NULL.DTPomoziMi.exception.IllegalActionException;
import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.RatingRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.RatingService;
import NULL.DTPomoziMi.service.RequestService;
import NULL.DTPomoziMi.service.UserService;
import NULL.DTPomoziMi.web.DTO.RatingDTO;

@Service
@PreAuthorize("isAuthenticated()")
public class RatingServiceImpl implements RatingService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RatingRepo ratingRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private RequestService requestService;

	@Override
	public Rating fetch(Long id) {
		return ratingRepo
			.findById(id)
			.orElseThrow(() -> new EntityMissingException(Rating.class, id));
	}

	@Override
	public Rating getRatingById(Long id, UserPrincipal principal) {
		User user = principal.getUser();

		Rating r = fetch(id);

		if (
			!r.getRated().getIdUser().equals(user.getIdUser())
				&& !r.getRator().getIdUser().equals(user.getIdUser())
				&& !user.getEnumRoles().contains(Role.ROLE_ADMIN)
		) r.setRequest(null);

		return r;
	}

	@Override
	public Rating deleteById(Long id, UserPrincipal principal) {
		User user = principal.getUser();
		Rating rating = fetch(id);

		if (!user.getIdUser().equals(rating.getRator().getIdUser()))
			throw new IllegalAccessException("Only author of the rating can delete the rating!");

		ratingRepo.deleteById(id);
		return rating;
	}

	@Override
	public Rating create(RatingDTO rating, long idUser, Long idRequest, UserPrincipal principal) {
		User user = principal.getUser();
		User rated = userService.fetch(idUser);

		Request req = null;
		if (idRequest != null) {
			req = requestService.fetch(idRequest);
			if (
				!req.getAuthor().getIdUser().equals(idUser)
					&& !req.getExecutor().getIdUser().equals(idUser)
			) throw new IllegalAccessException("Only authors and executors can rate requests!");

			if (!req.getStatus().equals(RequestStatus.FINALIZED))
				throw new IllegalActionException("Cannot rate request that is not finalized!");
		}

		Rating r = modelMapper.map(rating, Rating.class);

		r.setRequest(req);
		r.setRator(user);
		r.setRated(rated);

		return ratingRepo.save(r);
	}

	@Override
	public Rating update(RatingDTO rating, long ratingId, UserPrincipal principal) { // ne moze se updateat req, i ne mogu se mijenjat useri...
		if (rating.getIdRating() == null || !rating.getIdRating().equals(rating))
			throw new IllegalArgumentException("Rating id must be preserved!");

		Assert.notNull(rating.getRator(), "Rator must not be null!");

		User user = principal.getUser();
		if (!user.getIdUser().equals(rating.getRator().getIdUser()))
			throw new IllegalAccessException("Missing permission to update request!");

		Rating r = fetch(ratingId);
		r.setComment(rating.getComment());
		r.setRate(rating.getRate());

		return ratingRepo.save(r);
	}

	@Override
	public Page<Rating> findByRated(Pageable pageable, long userID) {
		Page<Rating> page = ratingRepo.findByRated(pageable, userID);
		page.forEach(r -> r.setRequest(null));
		return page;
	}

}
