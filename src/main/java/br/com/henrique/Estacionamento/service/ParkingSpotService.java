package br.com.henrique.Estacionamento.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.henrique.Estacionamento.model.ParkingSpotModel;
import br.com.henrique.Estacionamento.repository.ParkingSpotRepository;

@Service
public class ParkingSpotService {

	@Autowired
	private ParkingSpotRepository parkingSportRepository;

	// usando para metodos contrutivos ou destruitivo caso ocorra problema ele ja faz o rollback
	@Transactional
	public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
		return parkingSportRepository.save(parkingSpotModel);
	}


	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		return parkingSportRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return parkingSportRepository.existsByApartmentAndBlock(apartment, block);
	}

	public boolean existsByLicensePlateCar(String licensePlateCar) {
		return parkingSportRepository.existsByLicensePlateCar(licensePlateCar);
	}


	public Page<ParkingSpotModel> findAll(Pageable pageable) {
		return parkingSportRepository.findAll(pageable);
	}


	public Optional<ParkingSpotModel> findByLicensePlateCar(String licensePlateCar) {
		return parkingSportRepository.findByLicensePlateCar(licensePlateCar);
	}


	@Transactional
	public void delete(ParkingSpotModel parkingSpotModel) {
		parkingSportRepository.delete(parkingSpotModel);
	}

}
