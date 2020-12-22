package NULL.DTPomoziMi.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.repository.RatingRepo;
import NULL.DTPomoziMi.service.RatingService;
import NULL.DTPomoziMi.web.DTO.RatingDTO;

@Service
public class RatingServiceImpl implements RatingService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RatingRepo ratingRepo;

	@Override
	public Page<Rating> findAll(Pageable pageable) {
		return ratingRepo.findAll(pageable);
	}

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
			.findById(id).orElseThrow(() -> new EntityMissingException(Rating.class, id));
	}

	@Override
	public Optional<Rating> findById(Long id) {
		return ratingRepo.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return ratingRepo.existsById(id);
	}

	@Override
	public Iterable<Rating> findAll() {
		return ratingRepo.findAll();
	}

	@Override
	public long count() {
		return ratingRepo.count();
	}

	@Override
	public Rating deleteById(Long id) {
		Rating rating = fetch(id);
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

}
