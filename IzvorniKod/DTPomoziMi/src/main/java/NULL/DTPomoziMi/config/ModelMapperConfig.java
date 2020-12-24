package NULL.DTPomoziMi.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper
			.getConfiguration()
			.setPropertyCondition(Conditions.isNotNull())
			.setMatchingStrategy(MatchingStrategies.STRICT)
			.setFieldMatchingEnabled(true)
			.setFieldAccessLevel(AccessLevel.PRIVATE);
		return modelMapper;
	}

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
