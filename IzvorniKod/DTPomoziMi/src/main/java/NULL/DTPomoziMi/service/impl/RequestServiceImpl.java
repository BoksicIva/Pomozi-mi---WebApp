package NULL.DTPomoziMi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.repository.RequestRepo;
import NULL.DTPomoziMi.service.RequestService;
import NULL.DTPomoziMi.web.DTO.RequestDTO;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepo requestRepo;

    @Override
    public void createRequest(RequestDTO request) {
        //requestRepo.save(request);
        return;
    }

    @Override
    public void updateRequest(RequestDTO requestDTO) {
       Request request = requestRepo.findById(requestDTO.getIdRequest()).get();
       return;
    }

    @Override
    public void deleteRequest(Long id_zahtjev) {
        requestRepo.deleteById(id_zahtjev);
    }

    @Override
    public Request getRequestbyId(Long id_zahtjev) {
        return requestRepo.findById(id_zahtjev).get();
    }
    @Override
    public List<Request> findAll(){
        List<Request> all = new ArrayList<>();
        requestRepo.findAll().forEach(all::add);
        return all;
    }
}
