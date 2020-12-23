package NULL.DTPomoziMi.web.assemblers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import NULL.DTPomoziMi.model.Candidacy;
import NULL.DTPomoziMi.web.DTO.CandidacyDTO;
import NULL.DTPomoziMi.web.controller.CandidacyController;

@Component
public class CandidacyDTOAssembler
	extends RepresentationModelAssemblerSupport<Candidacy, CandidacyDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public CandidacyDTOAssembler() {
		super(CandidacyController.class, CandidacyDTO.class);
	}

	@Override
	public CandidacyDTO toModel(Candidacy entity) {
		CandidacyDTO candidacyDTO = createModelWithId(entity.getIdCandidacy(), entity);
		modelMapper.map(entity, candidacyDTO);

		return candidacyDTO;
	}

}
