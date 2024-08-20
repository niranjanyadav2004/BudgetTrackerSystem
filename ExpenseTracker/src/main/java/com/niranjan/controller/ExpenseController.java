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

import com.niranjan.DTO.ExpenseDTO;
import com.niranjan.entities.Expense;
import com.niranjan.service.ExpenseServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExpenseController {

	@Autowired
	private ExpenseServices expenseServices;
	
	@PostMapping
	public ResponseEntity<?> postExpense(@RequestBody ExpenseDTO expenseDTO){
		Expense expense = expenseServices.postExpense(expenseDTO);
		if(expense!=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(expense);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllExpenses(){
		return ResponseEntity.ok(expenseServices.getAllExpenses());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getExpenseById(@PathVariable Long id){
		try {
			return ResponseEntity.ok(expenseServices.getExpenseById(id));
		}
		catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateExpense(@PathVariable Long id,@RequestBody ExpenseDTO expenseDTO){
		 try {
			return ResponseEntity.ok(expenseServices.updateExpense(id, expenseDTO));
		} 
		 catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} 
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteExpense(@PathVariable Long id){
		 try {
			expenseServices.deleteExpense(id);
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
