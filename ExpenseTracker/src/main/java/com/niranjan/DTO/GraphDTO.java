package com.niranjan.DTO;

import java.util.List;

import com.niranjan.entities.Expense;
import com.niranjan.entities.Income;

import lombok.Data;

@Data
public class GraphDTO {

	private List<Expense> expenseList;
	
	private List<Income> incomeList;
	
}
