package com.niranjan.serviceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niranjan.DTO.ExpenseDTO;
import com.niranjan.entities.Expense;
import com.niranjan.repos.ExpenseRepository;
import com.niranjan.service.ExpenseServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseServices {

	@Autowired
	private ExpenseRepository expenseRepository;
	
	
	private Expense saveOrUpdateExpense(Expense expense,ExpenseDTO expenseDTO) {
	     expense.setTitle(expenseDTO.getTitle());
	     expense.setDate(expenseDTO.getDate());
	     expense.setAmount(expenseDTO.getAmount());
	     expense.setCategory(expenseDTO.getCategory());
	     expense.setDescription(expenseDTO.getDescription());
	     
	     return expenseRepository.save(expense);
	}

	@Override
	public Expense postExpense(ExpenseDTO expenseDTO) {
		return saveOrUpdateExpense(new Expense(), expenseDTO);
	}

	@Override
	public List<Expense> getAllExpenses() {
		return expenseRepository.findAll().stream()
				.sorted(Comparator.comparing(Expense::getDate).reversed())
				.collect(Collectors.toList());
	}

	@Override
	public Expense getExpenseById(Long id) {
		Optional<Expense> optional = expenseRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			throw new EntityNotFoundException("Expense is not present with id "+id);
		}
	}

	@Override
	public Expense updateExpense(Long id, ExpenseDTO expenseDTO) {
		Optional<Expense> optional = expenseRepository.findById(id);
		if(optional.isPresent()) {
			return saveOrUpdateExpense(optional.get(), expenseDTO);
		}
		else {
			throw new EntityNotFoundException("Expense is not present with id "+id);
		}
	}

	@Override
	public void deleteExpense(Long id) {
	     Optional<Expense> optional = expenseRepository.findById(id);
	     if(optional.isPresent()) {
	    	 expenseRepository.deleteById(id);
	     }
	     else {
	    	 throw new EntityNotFoundException("Expense is not present with id "+id);
	     }
	}
}
