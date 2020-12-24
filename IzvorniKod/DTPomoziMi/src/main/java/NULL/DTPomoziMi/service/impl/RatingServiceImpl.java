package NULL.DTPomoziMi.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.exception.IllegalAccessException;
import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.RatingRepo;
import NULL.DTPomoziMi.service.RatingService;
import NULL.DTPomoziMi.util.UserPrincipalGetter;
import NULL.DTPomoziMi.web.DTO.RatingDTO;

@Service
@PreAuthorize("isAuthenticated()")
public class RatingServiceImpl implements RatingService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RatingRepo ratingRepo;

	@Override
	public Page<Rating> findAll(Pageable pageable) { return ratingRepo.findAll(pageable); }

	@Override
	public Rating save(RatingDTO entity) {
		return ratingRepo.save(modelMapper.map(entity, Rating.class));
	}

	@Override
	public Iterable<Rating> saveAll(Iterable<RatingDTO> entities) {
		Stream<RatingDTO> stream = StreamSupport.stream(entities.spliterator(), false);
		return ratingRepo
			.saveAll(
				stream.map(r -> modelMapper.map(r, Rating.class)).collect(Collectors.toList())
			);
	}

	@Override
	public Rating fetch(Long id) {
		return ratingRepo
			.findById(id)
			.orElseThrow(() -> new EntityMissingException(Rating.class, id));
	}

	@Override
	public Optional<Rating> findById(Long id) { return ratingRepo.findById(id); }

	@Override
	public boolean existsById(Long id) { return ratingRepo.existsById(id); }

	@Override
	public Iterable<Rating> findAll() { return ratingRepo.findAll(); }

	@Override
	public long count() { return ratingRepo.count(); }

	@Override
	public Rating deleteById(Long id) {
		User user = UserPrincipalGetter.getPrincipal().getUser();
		Rating rating = fetch(id);

		if (!user.getIdUser().equals(rating.getRator().getIdUser()))
			throw new IllegalAccessException("Only author of the rating can delete the rating!");

		ratingRepo.deleteById(id);
		return rating;
	}

	@Override
	public Iterable<Rating> deleteAll(Iterable<RatingDTO> entities) {
		Stream<RatingDTO> stream = StreamSupport.stream(entities.spliterator(), false);
		Iterable<Rating> iterable
			= ratingRepo.findAllById(stream.map(r -> r.getIdRating()).collect(Collectors.toList()));
		ratingRepo.deleteAll(iterable);
		return iterable;
	}

	@Override
	public Iterable<Rating> deleteAll() {
		Iterable<Rating> iterable = ratingRepo.findAll();
		ratingRepo.deleteAll();
		return iterable;
	}

	@Override
	public Rating create(RatingDTO rating) {
		User user = UserPrincipalGetter.getPrincipal().getUser();
		Rating r = modelMapper.map(rating, Rating.class);
		r.setRator(user);

		return ratingRepo.save(r);
	}

	@Override
	public Rating update(RatingDTO rating, long ratingId) {
		if (rating.getIdRating() == null || !rating.getIdRating().equals(rating))
			throw new IllegalArgumentException("Rating id must be preserved!");

		User user = UserPrincipalGetter.getPrincipal().getUser();
		if (!user.getIdUser().equals(rating.getRator().getIdUser()))
			throw new IllegalAccessException("Missing permission to update request!");

		Rating r = fetch(ratingId);
		modelMapper.map(rating, r);
		return ratingRepo.save(r);
	}

}
