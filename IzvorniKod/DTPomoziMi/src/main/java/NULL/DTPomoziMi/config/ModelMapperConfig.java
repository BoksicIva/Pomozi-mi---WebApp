package NULL.DTPomoziMi.config;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import NULL.DTPomoziMi.model.Location;
import NULL.DTPomoziMi.model.Request;
import NULL.DTPomoziMi.model.User;
import NULL.DTPomoziMi.web.DTO.LocationDTO;
import NULL.DTPomoziMi.web.DTO.RequestDTO;
import NULL.DTPomoziMi.web.DTO.UserDTO;
import NULL.DTPomoziMi.web.assemblers.LocationDTOAssembler;
import NULL.DTPomoziMi.web.assemblers.RequestDTOAssembler;
import NULL.DTPomoziMi.web.assemblers.UserDTOModelAssembler;

@Configuration
public class ModelMapperConfig {
	private ModelMapper modelMapper;

	private UserDTOModelAssembler userAssembler;

	private LocationDTOAssembler locationAssembler;

	@Autowired
	public ModelMapperConfig(
		ModelMapper modelMapper, UserDTOModelAssembler userAssembler,
		LocationDTOAssembler locationAssembler, RequestDTOAssembler requestAssembler
	) {
		super();
		this.modelMapper = modelMapper;
		this.userAssembler = userAssembler;
		this.locationAssembler = locationAssembler;
		configureMapper();
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull())
			.setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
			.setFieldAccessLevel(AccessLevel.PRIVATE);
		return modelMapper;
	}

	private void configureMapper() {
		configureRequestToRequestDTO();

	}

	private void configureRequestToRequestDTO() {

		modelMapper.addMappings(new PropertyMap<Request, RequestDTO>() {
			@Override
			protected void configure() {
				using(userConverter).map(source.getAuthor()).setAuthor(null);
				using(userConverter).map(source.getExecutor()).setExecutor(null);
				using(locationConverter).map(source.getLocation()).setLocation(null);
				map().setDescription(source.getDescription());
				map().setIdRequest(source.getIdRequest());
				map().setPhone(source.getPhone());
				map().setStatus(source.getStatus());
				map().setTstmp(source.getTstmp());
			}
		});

	}

	Converter<User, UserDTO> userConverter = context -> (context.getSource() == null ? null
		: userAssembler.toModel(context.getSource()));

	Converter<Location, LocationDTO> locationConverter = context -> (context.getSource() == null
		? null : locationAssembler.toModel(context.getSource()));

	/*
	 * private void configureRequestDTOToRequest() { modelMapper.addMappings(new
	 * PropertyMap<RequestDTO, Request>() {
	 * 
	 * @Override protected void configure() { using(userDTOConverter).map() } }); }
	 * 
	 * Converter<UserDTO, User> userDTOConverter = context -> { // ako postoji u
	 * bazi pod tim id... bit ce dohvacen... UserDTO dto = context.getSource(); if
	 * (dto == null) return null;
	 * 
	 * if (dto.getIdUser() != null) { try { return
	 * userService.fetch(dto.getIdUser()); } catch (Exception ex) {} }
	 * 
	 * User user = new User(); user.setIdUser(dto.getIdUser());
	 * user.setEmail(dto.getEmail()); user.setFirstName(dto.getFirstName());
	 * user.setLastName(dto.getLastName()); user.setLocation(dto.getLocation());
	 * 
	 * return user; };
	 * 
	 * Converter<LocationDTO, Location> locationDTOConverter = context -> {
	 * LocationDTO dto = context.getSource(); if (dto == null) return null;
	 * 
	 * if (dto.getIdLocation() != null) { try { return
	 * LocationService.fetch(dto.getIdLocation()); } catch (Exception ex) {} }
	 * 
	 * try { Location loc =
	 * LocationService.findByLatitudeAndLongitude(dto.getLatitude(),
	 * dto.getLongitude()); if (loc != null) return loc;
	 * 
	 * } catch (Exception e2) {}
	 * 
	 * Location loc = new Location(); loc.setAdress(dto.getAdress());
	 * loc.setLatitude(dto.getLatitude()); loc.setLongitude(dto.getLongitude());
	 * loc.setState(dto.getState()); loc.setTown(dto.getTown());
	 * 
	 * return loc; };
	 */

}
