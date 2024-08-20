package com.niranjan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niranjan.DTO.IncomeDTO;
import com.niranjan.entities.Income;
import com.niranjan.service.IncomeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
@CrossOrigin("*")
public class IncomeController {

	@Autowired
	private final IncomeService incomeService;
	
	@PostMapping()
	public ResponseEntity<?> postIncome(@RequestBody IncomeDTO incomeDTO){
		Income postIncome = incomeService.postIncome(incomeDTO);
		if(postIncome!=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(postIncome);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllIncome(){
		return ResponseEntity.ok(incomeService.getAllIncome());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getIncomeById(@PathVariable Long id){
		try {
			return ResponseEntity.ok(incomeService.getIncomeById(id));
		} 
		catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateIncome(@PathVariable Long id, @RequestBody IncomeDTO incomeDTO){
		try {
			return ResponseEntity.ok(incomeService.updateIncome(id, incomeDTO));
		} 
		catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIncome(@PathVariable Long id){
		try {
			incomeService.deleteIncome(id);
			return ResponseEntity.ok(null);
		} 
		catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}
	
}
