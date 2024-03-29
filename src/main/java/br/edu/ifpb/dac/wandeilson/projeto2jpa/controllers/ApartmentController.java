package br.edu.ifpb.dac.wandeilson.projeto2jpa.controllers;

import br.edu.ifpb.dac.wandeilson.projeto2jpa.dtos.ApartmentDTO;
import br.edu.ifpb.dac.wandeilson.projeto2jpa.models.Apartment;
import jakarta.validation.Valid;
import br.edu.ifpb.dac.wandeilson.projeto2jpa.services.ApartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/apartment")
public class ApartmentController {
	
	private final ApartmentService apartmentService;

	public ApartmentController(ApartmentService apartmentService) {
		this.apartmentService = apartmentService;
	}

	@PostMapping
	public ResponseEntity<Object> create (@RequestBody @Valid Apartment apartment) {
		return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.create(apartment));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteById( @PathVariable(value = "id") Long id){
		apartmentService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update ( @PathVariable(value = "id") Long id, @RequestBody @Valid ApartmentDTO apartmentDTO){
		try {
			 return ResponseEntity.ok(apartmentService.update(apartmentDTO, id));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@PutMapping("/{id}/{idParkingSpot}")
	public ResponseEntity<Object> update ( @PathVariable(value = "id") Long id, @PathVariable(value = "idParkingSpot") Long idParkingSpot){
		try {
			return ResponseEntity.ok(apartmentService.updateParkingSpot(id, idParkingSpot));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping
	public ResponseEntity<Object> showAll() {
		return ResponseEntity.status(HttpStatus.OK).body( apartmentService.showAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> readByID ( @PathVariable (value = "id") Long id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(apartmentService.readById(id));
		} catch (Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}

	public void setParkingSpot(Long idApartment, Long idParkingSpot){
		apartmentService.setParkingSpot( idApartment ,idParkingSpot);
	}
}
