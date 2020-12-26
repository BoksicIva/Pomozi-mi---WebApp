package NULL.DTPomoziMi.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.exception.IllegalAccessException;
import NULL.DTPomoziMi.exception.IllegalActionException;
import NULL.DTPomoziMi.model.Location;
import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.model.Role;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.repository.RequestRepo;
import NULL.DTPomoziMi.security.UserPrincipal;
import NULL.DTPomoziMi.service.LocationService;
import NULL.DTPomoziMi.service.RequestService;
import NULL.DTPomoziMi.web.DTO.CreateRequestDTO;
import NULL.DTPomoziMi.web.DTO.LocationDTO;
import NULL.DTPomoziMi.web.DTO.RequestDTO;
import NULL.DTPomoziMi.web.assemblers.RequestDTOAssembler;

@PreAuthorize(value = "isAuthenticated()")
@Service
public class RequestServiceImpl implements RequestService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RequestRepo requestRepo;

	@Autowired
	private LocationService locationService;

	@Autowired
	private RequestDTOAssembler requestDTOAssembler;

	@Override
	public Request createRequest(CreateRequestDTO request, UserPrincipal principal) {
		Request req = modelMapper.map(request, Request.class);

		LocationDTO location = request.getLocation();

		Location loc = resolveLocation(location);
		if (loc == null && location != null) {
			loc = modelMapper.map(req.getLocation(), Location.class);
			loc.setIdLocation(null);
		}
		req.setLocation(loc);

		req.setAuthor(principal.getUser());
		req.setRecivedNotif(false);
		req.setStatus(RequestStatus.ACTIVE);

		return requestRepo.save(req);
	}

	@Override
	public Request updateRequest(long idRequest, RequestDTO requestDTO, UserPrincipal principal) {
		if (!requestDTO.getIdRequest().equals(idRequest))
			throw new IllegalArgumentException("Request id must be preserved!");

		User user = principal.getUser();
		Request req = fetch(idRequest);

		LocationDTO location = requestDTO.getLocation();
		Location loc = resolveLocation(location);
		if (loc == null && location != null) {
			loc = modelMapper.map(location, Location.class);
			loc.setIdLocation(null);
		}
		req.setLocation(loc);

		req.setDescription(requestDTO.getDescription());
		req.setPhone(requestDTO.getPhone());
		req.setTstmp(requestDTO.getTstmp());

		if (!user.getIdUser().equals(req.getAuthor().getIdUser()))
			throw new IllegalAccessException("Only authors can modify requests!");

		return requestRepo.save(req);
	}

	@Override
	public Request deleteRequest(long idRequest, UserPrincipal principal) {
		Request req = fetch(idRequest);
		User user = principal.getUser();

		if (
			!user.getIdUser().equals(req.getAuthor().getIdUser())
				&& !user.getEnumRoles().contains(Role.ROLE_ADMIN)
		) throw new IllegalAccessException("Missing permissions to delete the request!");

		if (req.getExecutor() != null) // samo aktivni se smiju obrisati samo tako...
			throw new IllegalActionException("Cannot delete request that has an executor!");

		requestRepo.deleteById(idRequest);
		req.setStatus(RequestStatus.DELETED);
		return req;
	}

	@Override
	public Request blockRequest(long idRequest, UserPrincipal principal) {
		Request r = fetch(idRequest);
		User user = principal.getUser();

		if (
			!user.getIdUser().equals(r.getAuthor().getIdUser())
				&& !user.getEnumRoles().contains(Role.ROLE_ADMIN)
		) throw new IllegalAccessException("Missing permissions to block the request!");

		r.setStatus(RequestStatus.BLOCKED);

		return requestRepo.save(r);
	}

	@Override
	public Request pickForExecution(long idRequest, UserPrincipal principal) { // TODO ide notifikacija!! ... sto ako... autor ne zeli da mu ovaj izvede..?
		User user = principal.getUser();
		Request r = fetch(idRequest);

		if (!r.getStatus().equals(RequestStatus.ACTIVE))
			throw new IllegalActionException("Cannot pick non active request for execution!");

		r.setExecutor(user);
		r.setStatus(RequestStatus.EXECUTING);

		return requestRepo.save(r);
	}

	@Override
	public Request markExecuted(long idRequest, UserPrincipal principal) { // TODO ide ocjenjivanje
		User user = principal.getUser();
		Request r = fetch(idRequest);

		if (!user.getIdUser().equals(r.getAuthor().getIdUser()))
			throw new IllegalAccessException("Only author can mark request as executed!");

		if (
			!r.getStatus().equals(RequestStatus.EXECUTING)
		) throw new IllegalActionException(
			"Cannot mark a request without an executor as executed!"
		);

		r.setStatus(RequestStatus.FINALIZED);
		r.setExecTstmp(LocalDateTime.now());

		return requestRepo.save(r);
	}

	@Override
	public Request backOff(long id, UserPrincipal principal) {
		User user = principal.getUser();
		Request req = fetch(id);

		if (req.getExecutor() == null || !user.getIdUser().equals(req.getExecutor().getIdUser()))
			throw new IllegalAccessException("Missing permission to change request status");

		if (!req.getStatus().equals(RequestStatus.EXECUTING))
			throw new IllegalActionException("Cannot backoff from request!");

		req.setStatus(RequestStatus.ACTIVE);
		req.setExecutor(null);

		return requestRepo.save(req);
	}

	@Override
	public Request fetch(long requestId) {
		return requestRepo
			.findById(requestId)
			.orElseThrow(() -> new EntityMissingException(Request.class, requestId));
	}

	@Override
	public Request getRequestbyId(long idRequest, UserPrincipal principal) {
		Request req = fetch(idRequest);
		User user = principal.getUser();

		if (
			user.getIdUser().equals(req.getAuthor().getIdUser())
				|| (req.getExecutor() != null
					&& user.getIdUser().equals(req.getExecutor().getIdUser()))
				|| user.getEnumRoles().contains(Role.ROLE_ADMIN)
		) return req;

		if (req.getStatus().equals(RequestStatus.ACTIVE)) {
			hideInfo(req);
		} else {
			throw new IllegalAccessException(
				"User with id: " + user.getIdUser() + "is trying to access request with id: "
					+ req.getIdRequest() + " without permission."
			);
		}

		return req;
	}

	private void hideInfo(Request req) { req.setRecivedNotif(null); req.setPhone(null); }

	@Override
	public Page<Request> getAllActiveRequests(
		Pageable pageable, Double radius, UserPrincipal principal
	) {
		List<Request> actives
			= requestRepo.findByStatusOrderByIdRequest(RequestStatus.ACTIVE).stream().filter(r -> {
				User user = principal.getUser();
				boolean in = false;
				if (r.getLocation() == null) // ako zahtjev nema lokaciju... moze
					in = true;
				else if (user.getLocation() != null && radius != null) { // ako user ima lokaciju i radius je dan
					Location loc = user.getLocation();
					double distance = calculateDistanceInKM(loc, r.getLocation());
					in = distance <= radius;
				}
				return in;
			}).collect(Collectors.toList());

		long start = pageable.getOffset();
		long end = (start + pageable.getPageSize()) > actives.size() ? actives.size()
			: (start + pageable.getPageSize());

		if (start >= end) return new PageImpl<>(new ArrayList<>(), pageable, 0);

		return new PageImpl<>(actives.subList((int) start, (int) end), pageable, actives.size());
	}

	private double calculateDistanceInKM(Location l1, Location l2) {
		double lat1 = l1.getLatitude().doubleValue(), lon1 = l1.getLongitude().doubleValue();
		double lat2 = l2.getLatitude().doubleValue(), lon2 = l2.getLongitude().doubleValue();

		//Haversine formula 
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		// convert to radians 
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// apply formulae 
		double a = Math.pow(Math.sin(dLat / 2), 2)
			+ Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double rad = 6371; //radius zemlje
		double c = 2 * Math.asin(Math.sqrt(a));
		return rad * c;
	}

	@Override
	public Map<String, CollectionModel<RequestDTO>> getAuthoredRequests(
		long userID, UserPrincipal principal
	) {
		User user = principal.getUser(); // principal exists in the context because user has to be authenticated before accessing this point
		if (
			!user.getIdUser().equals(userID)
		) throw new IllegalAccessException("ID of logged in user is not the same as given userID!");

		List<Request> active = requestRepo.findByStatusAndAuthor(RequestStatus.ACTIVE, user);
		List<Request> finalized = requestRepo.findByStatusAndAuthor(RequestStatus.FINALIZED, user);
		List<Request> blocked = requestRepo.findByStatusAndAuthor(RequestStatus.BLOCKED, user);

		Map<String, CollectionModel<RequestDTO>> map = new HashMap<>();

		map.put(RequestStatus.ACTIVE.toString(), requestDTOAssembler.toCollectionModel(active));
		map
			.put(
				RequestStatus.FINALIZED.toString(), requestDTOAssembler.toCollectionModel(finalized)
			);
		map.put(RequestStatus.BLOCKED.toString(), requestDTOAssembler.toCollectionModel(blocked));

		return map;
	}

	private Location resolveLocation(LocationDTO dto) {
		if (dto != null) { // ako je dana lokacija onda provjeri postoji li vec spremljena pa ju dodaj u req ili... ako ne onda spremi i dodaj u req 
			try {
				Location loc = locationService
					.findByLatitudeAndLongitude(dto.getLatitude(), dto.getLongitude());

				if (loc == null) loc = locationService.save(dto);

				return loc;
			} catch (Exception e) {}
		}

		return null;
	}

}