package NULL.DTPomoziMi.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Location;
import NULL.DTPomoziMi.repository.LocationRepo;
import NULL.DTPomoziMi.service.LocationService;
import NULL.DTPomoziMi.web.DTO.LocationDTO;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LocationRepo locationRepo;

	@Override
	public Location save(LocationDTO entity) {
		return locationRepo.save(modelMapper.map(entity, Location.class));
	}

	@Override
	public Iterable<Location> saveAll(Iterable<LocationDTO> entities) {
		Stream<LocationDTO> stream = StreamSupport.stream(entities.spliterator(), false);
		return locationRepo
			.saveAll(
				stream.map(l -> modelMapper.map(l, Location.class)).collect(Collectors.toList())
			);
	}

	@Override
	public Optional<Location> findById(Long id) {
		return locationRepo.findById(id);
	}

	@Override
	public Location fetch(Long id) {
		return locationRepo
			.findById(id).orElseThrow(() -> new EntityMissingException(Location.class, id));
	}

	@Override
	public boolean existsById(Long id) {
		return locationRepo.existsById(id);
	}

	@Override
	public Iterable<Location> findAll() {
		return locationRepo.findAll();
	}

	@Override
	public Iterable<Location> findAllById(Iterable<Long> ids) {
		return locationRepo.findAllById(ids);
	}

	@Override
	public long count() {
		return locationRepo.count();
	}

	@Override
	public Location deleteById(Long id) {
		Location location = fetch(id);
		locationRepo.deleteById(id);
		return location;
	}

	@Override
	public Iterable<Location> deleteAll(Iterable<LocationDTO> entities) {
		Stream<LocationDTO> stream = StreamSupport.stream(entities.spliterator(), false);
		Iterable<Location> iterable
			= locationRepo
				.findAllById(stream.map(l -> l.getIdLocation()).collect(Collectors.toList()));
		locationRepo.deleteAll(iterable);
		return iterable;
	}

	@Override
	public Iterable<Location> deleteAll() {
		Iterable<Location> iterable = locationRepo.findAll();
		locationRepo.deleteAll();
		return iterable;
	}
	
	@Override
	public Location findByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude) {
		return locationRepo.findByLatitudeAndLongitude(latitude, longitude);
	}

}
