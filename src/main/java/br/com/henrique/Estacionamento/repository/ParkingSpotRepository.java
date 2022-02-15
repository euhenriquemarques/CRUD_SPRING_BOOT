package br.com.henrique.Estacionamento.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.henrique.Estacionamento.model.ParkingSpotModel;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID>{
	
	boolean existsByLicensePlateCar(String licesePlateCar);

	boolean existsByParkingSpotNumber(String parkingSpotNumber);

	boolean existsByApartmentAndBlock(String apartment, String block);

	Optional<ParkingSpotModel> findByLicensePlateCar(String licensePlateCar);
	
}
