package NULL.DTPomoziMi.service.impl;

import NULL.DTPomoziMi.model.Rating;
import NULL.DTPomoziMi.model.RequestStatus;
import NULL.DTPomoziMi.web.DTO.RatingDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.exception.EntityMissingException;
import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.repository.RequestRepo;
import NULL.DTPomoziMi.service.RequestService;
import NULL.DTPomoziMi.web.DTO.RequestDTO;

import java.util.*;

@Service
public class RequestServiceImpl implements RequestService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RequestRepo requestRepo;

	@Override
	public Request createRequest(RequestDTO request) {
		Request saved = requestRepo.save(modelMapper.map(request, Request.class));
		return saved;
	}

	@Override
	public Request updateRequest(RequestDTO requestDTO) { //FIXME validate ?
		if (!requestRepo.existsById(requestDTO.getIdRequest()))
			throw new EntityMissingException(Request.class, requestDTO.getIdRequest());

		return requestRepo.save(modelMapper.map(requestDTO, Request.class));
	}

	@Override
    public Request deleteRequest(long idRequest) {
    	Request req = fetch(idRequest);
        requestRepo.deleteById(idRequest);
        return req;
    }

    @Override
	public Request blockRequest(RequestDTO request) {
		Request r = fetch(request.getIdRequest());
		r.setStatus(RequestStatus.BLOKIRAN);
		return r;
	}
	@Override
	public Request executeRequest(RequestDTO request) {
		Request r = fetch(request.getIdRequest());
		r.setStatus(RequestStatus.IZVRSEN);
		return r;
	}

	@Override
	public Request fetch(long requestId) {
		return requestRepo
				.findById(requestId)
				.orElseThrow(() -> new EntityMissingException(Request.class, requestId));
	}

	@Override
	public Request getRequestbyId(long id_zahtjev) {
		return fetch(id_zahtjev);
	}

	@Override
	public Page<Request> findAll(Pageable pageable) {
		return requestRepo.findAll(pageable);
	}

	@Override
	public HashMap<String, List<Request>> getMyReq(long userID) {
		Iterable<Request> all = requestRepo.findAll();

		List<Request> active = new ArrayList<>();
		List<Request> done = new ArrayList<>();

		for (Request r : all) {
			if (r.getAuthor().getIdUser() == userID) {
				if (r.getStatus().equals(RequestStatus.AKTIVAN)) {
					active.add(r);
				} else if (r.getStatus().equals(RequestStatus.IZVRSEN)) {
					done.add(r);
				}
			}
		}
		HashMap<String, List<Request>> m = new HashMap<>();
		m.put("aktivni", active);
		m.put("izvrseni", done);
		return m;
	}
}
