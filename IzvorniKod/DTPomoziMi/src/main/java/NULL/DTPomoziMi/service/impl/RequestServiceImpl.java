package NULL.DTPomoziMi.service.impl;

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
	public Request fetch(long requestId) {
		return requestRepo
				.findById(requestId)
				.orElseThrow(() -> new EntityMissingException(Request.class, requestId));
	}

	@Override
	public Request getRequestbyId(long id_zahtjev) {
		return requestRepo.findById(id_zahtjev).get();
	}

	@Override
	public Page<Request> findAll(Pageable pageable) {
		return requestRepo.findAll(pageable);
	}
}
