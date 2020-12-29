package NULL.DTPomoziMi.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Candidacy;
import NULL.DTPomoziMi.model.Location;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.CandidacyRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.CandidacyService;
import NULL.DTPomoziMi.service.LocationService;

@Service
@PreAuthorize("isAuthenticated()")
public class CandidacyServiceImpl implements CandidacyService {

	@Autowired
	private CandidacyRepo candidacyRepo;

	@Autowired
	private LocationService locationService;

	@Override
	public Candidacy fetch(Long id) {
		return candidacyRepo
			.findById(id)
			.orElseThrow(() -> new EntityMissingException(Candidacy.class, id));
	}

	@Override
	public long count() { return candidacyRepo.count(); }

	@Override
	public Page<Candidacy> getCandidacies(Pageable pageable, Specification<Candidacy> spec) {
		Page<Candidacy> page = candidacyRepo.findAll(spec, pageable);
		page.forEach(c -> c.getUsers().forEach(u -> u.setLocation(null)));

		return page;
	}

	@Override
	public Candidacy candidateYourself(UserPrincipal principal) {
		User user = principal.getUser();
		int year = LocalDate.now().getYear();

		List<Candidacy> list = candidacyRepo.findByYear(year);
		if (list.size() > 1) throw new RuntimeException("Too many candidacies for year: " + year);

		Location loc = resolveLocation();
		Candidacy can = null;
		if (list.size() != 0)
			can = list.get(0);
		else {
			can = new Candidacy();
			can.setLocation(loc);
			can.setYear(year);
		}

		can.getUsers().add(user);

		can = candidacyRepo.save(can);
		can.getUsers().forEach(u -> u.setLocation(null));
		return can;
	}

	private Location resolveLocation() {
		BigDecimal bd = new BigDecimal(-1000);
		Location loc = locationService.findByLatitudeAndLongitude(bd, bd);

		if (loc != null) return loc;

		loc = new Location();
		loc.setAdress("global");
		loc.setState("global");
		loc.setTown("global");
		loc.setLatitude(bd);
		loc.setLongitude(bd);
		
		return locationService.save(loc);
	}

	@Override
	public Candidacy deleteCandidacy(UserPrincipal principal) {
		User user = principal.getUser();
		int year = LocalDate.now().getYear();

		List<Candidacy> list = candidacyRepo.findByYear(year);
		if (list.size() > 1) throw new RuntimeException("Too many candidacies for year: " + year);

		Candidacy can = null;
		if (list.size() != 0) can = list.get(0);

		if (can != null) { can.getUsers().remove(user); }

		return can == null ? new Candidacy() : can;
	}

}
