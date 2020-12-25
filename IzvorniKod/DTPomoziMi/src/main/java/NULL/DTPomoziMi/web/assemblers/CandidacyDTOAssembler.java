package NULL.DTPomoziMi.web.assemblers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import NULL.DTPomoziMi.model.Candidacy;
import NULL.DTPomoziMi.model.Location;
import NULL.DTPomoziMi.web.DTO.CandidacyDTO;
import NULL.DTPomoziMi.web.DTO.LocationDTO;
import NULL.DTPomoziMi.web.controller.CandidacyController;

@Component
public class CandidacyDTOAssembler extends RepresentationModelAssemblerSupport<
	Candidacy, CandidacyDTO> {

	private ModelMapper modelMapper;

	@Autowired
	public CandidacyDTOAssembler(ModelMapper modelMapper, LocationDTOAssembler locationAssembler) {
		super(CandidacyController.class, CandidacyDTO.class);
		this.modelMapper = modelMapper;
		configureCandidacyToCandidacyDTO(locationAssembler);
	}

	@Override
	public CandidacyDTO toModel(Candidacy entity) {
		CandidacyDTO candidacyDTO = createModelWithId(entity.getIdCandidacy(), entity);
		modelMapper.map(entity, candidacyDTO);

		return candidacyDTO;
	}

	private void configureCandidacyToCandidacyDTO(LocationDTOAssembler locationAssembler) {

		Converter<Location, LocationDTO> locationConverter = context -> (context.getSource() == null
			? null : locationAssembler.toModel(context.getSource()));

		modelMapper.addMappings(new PropertyMap<Candidacy, CandidacyDTO>() {
			@Override
			protected void configure() {
				using(locationConverter).map(source.getLocation()).setLocation(null);
				map().setIdCandidacy(source.getIdCandidacy());
				map().setYear(source.getYear());
			}
		});

	}

}
