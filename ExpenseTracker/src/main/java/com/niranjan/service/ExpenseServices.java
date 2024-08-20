package com.niranjan.service;

import java.util.List;

import com.niranjan.DTO.ExpenseDTO;
import com.niranjan.entities.Expense;

public interface ExpenseServices {
     Expense postExpense(ExpenseDTO expenseDTO);
     
     List<Expense> getAllExpenses();
     
     Expense getExpenseById(Long id);
     
     Expense updateExpense(Long id,ExpenseDTO expenseDTO);
     
     void deleteExpense(Long id);
}
