package com.niranjan.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niranjan.DTO.GraphDTO;
import com.niranjan.DTO.StatsDTO;
import com.niranjan.entities.Expense;
import com.niranjan.entities.Income;
import com.niranjan.repos.ExpenseRepository;
import com.niranjan.repos.IncomeRepository;
import com.niranjan.service.StatsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

	@Autowired
	private final ExpenseRepository expenseRepository;
	
	@Autowired
	private final IncomeRepository incomeRepository;

	@Override
	public GraphDTO getChartData() {
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(27);
		
		GraphDTO graphDTO = new GraphDTO();
		graphDTO.setExpenseList(expenseRepository.findByDateBetween(startDate, endDate));
		graphDTO.setIncomeList(incomeRepository.findByDateBetween(startDate, endDate));
		
		return graphDTO;
	}

	@Override
	public StatsDTO getStats() {
		Double totalIncome = incomeRepository.sumAllAmount();
		Double totalExpense = expenseRepository.sumAllAmount();
		
		Optional<Income> optionalIncome = incomeRepository.findFirstByOrderByDateDesc();
		Optional<Expense> optionalExpense = expenseRepository.findFirstByOrderByDateDesc();
		
		StatsDTO statsDTO = new StatsDTO();
		statsDTO.setExpense(totalExpense);
		statsDTO.setIncome(totalIncome);
		
		optionalIncome.ifPresent(statsDTO::setLatestIncome);
		optionalExpense.ifPresent(statsDTO::setLastestExpense);
		
		statsDTO.setBalance(totalIncome-totalExpense);
		
		List<Income> incomeList = incomeRepository.findAll();
		List<Expense> expenseList = expenseRepository.findAll();
		
		OptionalDouble minIncome = incomeList.stream().mapToDouble(Income::getAmount).min();
		OptionalDouble maxIncome = incomeList.stream().mapToDouble(Income::getAmount).max();

		OptionalDouble minExpense = expenseList.stream().mapToDouble(Expense::getAmount).min();
		OptionalDouble maxExpense = expenseList.stream().mapToDouble(Expense::getAmount).max();
		
		statsDTO.setMinIncome(minIncome.isPresent() ? minIncome.getAsDouble() : null);
		statsDTO.setMaxIncome(maxIncome.isPresent() ? maxIncome.getAsDouble() : null);
		
		statsDTO.setMinExpense(minExpense.isPresent() ? minExpense.getAsDouble() : null);
		statsDTO.setMaxExpense(maxExpense.isPresent() ? maxExpense.getAsDouble() : null);
		
		return statsDTO;
	}
	
	
	
}
