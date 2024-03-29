package br.edu.ifpb.dac.wandeilson.projeto2jpa.services;

import br.edu.ifpb.dac.wandeilson.projeto2jpa.dtos.ApartmentDTO;
import br.edu.ifpb.dac.wandeilson.projeto2jpa.models.ParkingSpot;
import br.edu.ifpb.dac.wandeilson.projeto2jpa.repositories.ParkingSpotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.dac.wandeilson.projeto2jpa.models.Apartment;
import br.edu.ifpb.dac.wandeilson.projeto2jpa.repositories.ApartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	@Autowired
	private ParkingSpotRepository parkingSpotRepository;
	
	public ApartmentDTO create(Apartment apartment) {
		ApartmentDTO apartmentDTO = new ApartmentDTO();
		BeanUtils.copyProperties(apartmentRepository.save(apartment),
				apartmentDTO);
		return apartmentDTO;
	}

    public void deleteById(Long idApartment) {
		apartmentRepository.deleteById(idApartment);
    }

	public ApartmentDTO update(ApartmentDTO apartmentDTO, Long id) throws Exception {
		Optional<Apartment> apartmentSaved = apartmentRepository.findById(id);
		if(apartmentSaved.isPresent()){
			Apartment apt = apartmentSaved.get();
			BeanUtils.copyProperties(apartmentDTO,apt,"idApartment");
			apartmentRepository.save(apt);
			return apartmentDTO;
		}
		throw new Exception("Error when updating.");
	}


	public ApartmentDTO updateParkingSpot(Long id, Long idParkingSpot) throws Exception {
		Optional<Apartment> apartmentSaved = apartmentRepository.findById(id);
		if(apartmentSaved.isPresent()){
			Apartment apt = apartmentSaved.get();
			Optional<ParkingSpot> pkSpotSaved = parkingSpotRepository.findById(idParkingSpot);
			if (pkSpotSaved.isPresent()){
				ParkingSpot pkSpot = pkSpotSaved.get();
				apt.setParkingSpot(pkSpot);
			}
			ApartmentDTO aptDTO = new ApartmentDTO();
			BeanUtils.copyProperties(apt,aptDTO);
			apartmentRepository.save(apt);
			return aptDTO;
		}
		throw new Exception("Error when updating.");
	}

	public ApartmentDTO readById(Long idApartment) throws Exception {
		Optional<Apartment> apartmentSaved = apartmentRepository.findById(idApartment);
		if(apartmentSaved.isPresent()){
			Apartment apt = apartmentSaved.get();
			ApartmentDTO apartmentDTO = new ApartmentDTO();
			BeanUtils.copyProperties(apt,apartmentDTO);
			return apartmentDTO;
		}
		throw new Exception("Error when searching for apartment.");
	}

	public List<ApartmentDTO> showAll() {
		List<Apartment> apartments = apartmentRepository.findAll();
		List<ApartmentDTO> apartmentDTOS = new ArrayList<>();
		for (Apartment apt: apartments) {
			ApartmentDTO aptDTO = new ApartmentDTO();
			BeanUtils.copyProperties(apt,aptDTO);
			apartmentDTOS.add(aptDTO);
		}
		return apartmentDTOS;
	}

	public void setParkingSpot(Long idParkingSpot, Long parkingSpot) {
		Optional<Apartment> aptOptional = apartmentRepository.findById(idParkingSpot);
		if (aptOptional.isPresent()){
			Apartment apt = aptOptional.get();
			Optional<ParkingSpot> pkSpotOptional = parkingSpotRepository.findById(idParkingSpot);
			if(pkSpotOptional.isPresent()){
				ParkingSpot pkSpot = pkSpotOptional.get();
				apt.setParkingSpot(pkSpot);
				apartmentRepository.save(apt);
			}
		}

	}
}
