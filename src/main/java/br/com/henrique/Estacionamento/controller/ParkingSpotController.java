package br.com.henrique.Estacionamento.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.henrique.Estacionamento.dto.ParkingSpotDTO;
import br.com.henrique.Estacionamento.model.ParkingSpotModel;
import br.com.henrique.Estacionamento.service.ParkingSpotService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

	@Autowired
	private ParkingSpotService parkingSpotService;

	@RequestMapping(path = "/teste", method = RequestMethod.GET)
	public String buscar() {
		return "ok denovo";

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingDto) {

		if (parkingSpotService.existsByLicensePlateCar(parkingDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Licese Plate Car is already in use!");
		}

		if (parkingSpotService.existsByParkingSpotNumber(parkingDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
		}

		if (parkingSpotService.existsByApartmentAndBlock(parkingDto.getApartment(), parkingDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Conflict: Parking Spot already registered for apartment/block!");
		}

		ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingDto, parkingSpotModel);
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));

	}

	// fiz com a placa pra teste, pois o mariadb salva o id diferente do que e
	// informado na tela
	@RequestMapping(method = RequestMethod.GET, path = "/{placa}")
	public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "placa") String placa) {

		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findByLicensePlateCar(placa);

		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());

	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{placa}")
	public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "placa") String placa) {

		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findByLicensePlateCar(placa);

		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
		}
		parkingSpotService.delete(parkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());

	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{placa}")
	public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "placa") String placa,
			@RequestBody @Valid ParkingSpotDTO parkingDto) {

		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findByLicensePlateCar(placa);

		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
		}

		ParkingSpotModel parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingDto, parkingSpotModel);
		parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
		parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());

		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));

	}
}
