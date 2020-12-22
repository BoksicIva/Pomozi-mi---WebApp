package NULL.DTPomoziMi.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Candidacy;
import NULL.DTPomoziMi.repository.CandidacyRepo;
import NULL.DTPomoziMi.service.CandidacyService;
import NULL.DTPomoziMi.web.DTO.CandidacyDTO;

public class CandidacyServiceImpl implements CandidacyService {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CandidacyRepo candidacyRepo;

	@Override
	public Candidacy save(CandidacyDTO entity) {
		return candidacyRepo.save(modelMapper.map(entity, Candidacy.class));
	}

	@Override
	public Iterable<Candidacy> saveAll(Iterable<CandidacyDTO> entities) {
		Stream<CandidacyDTO> stream = StreamSupport.stream(entities.spliterator(), false);
		return candidacyRepo
			.saveAll(
				stream.map(l -> modelMapper.map(l, Candidacy.class)).collect(Collectors.toList())
			);
	}

	@Override
	public Optional<Candidacy> findById(Long id) {
		return candidacyRepo.findById(id);
	}

	@Override
	public Candidacy fetch(Long id) {
		return candidacyRepo
			.findById(id).orElseThrow(() -> new EntityMissingException(Candidacy.class, id));
	}

	@Override
	public boolean existsById(Long id) {
		return candidacyRepo.existsById(id);
	}

	@Override
	public Iterable<Candidacy> findAll() {
		return candidacyRepo.findAll();
	}

	@Override
	public Iterable<Candidacy> findAllById(Iterable<Long> ids) {
		return candidacyRepo.findAllById(ids);
	}

	@Override
	public long count() {
		return candidacyRepo.count();
	}

	@Override
	public Candidacy deleteById(Long id) {
		Candidacy candidacy = fetch(id);
		candidacyRepo.deleteById(id);
		return candidacy;
	}

	@Override
	public Iterable<Candidacy> deleteAll(Iterable<CandidacyDTO> entities) {
		Stream<CandidacyDTO> stream = StreamSupport.stream(entities.spliterator(), false);
		Iterable<Candidacy> iterable
			= candidacyRepo
				.findAllById(stream.map(l -> l.getIdCandidacy()).collect(Collectors.toList()));
		candidacyRepo.deleteAll(iterable);
		return iterable;
	}

	@Override
	public Iterable<Candidacy> deleteAll() {
		Iterable<Candidacy> iterable = candidacyRepo.findAll();
		candidacyRepo.deleteAll();
		return iterable;
	}

}
