package com.niranjan.serviceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niranjan.DTO.IncomeDTO;
import com.niranjan.entities.Income;
import com.niranjan.repos.IncomeRepository;
import com.niranjan.service.IncomeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {
	
	@Autowired
	private final IncomeRepository incomeRepository;
	
	private Income saveOrUpdateIncome(Income income,IncomeDTO incomeDTO) {
		income.setTitle(incomeDTO.getTitle());
		income.setDate(incomeDTO.getDate());
		income.setAmount(incomeDTO.getAmount());
		income.setCategory(incomeDTO.getCategory());
		income.setDescription(incomeDTO.getDescription());
		
		return incomeRepository.save(income);
	}
	
	@Override
	public Income postIncome(IncomeDTO incomeDTO) {
		return saveOrUpdateIncome(new Income(), incomeDTO);
	}

	@Override
	public List<IncomeDTO> getAllIncome() {
		return incomeRepository.findAll().stream().
				sorted(Comparator.comparing(Income::getDate).reversed())
				.map(Income::getIncomeDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Income updateIncome(Long id, IncomeDTO incomeDTO) {
		Optional<Income> optional = incomeRepository.findById(id);
		if(optional.isPresent()) {
			return saveOrUpdateIncome(optional.get(), incomeDTO);
		}
		else {
			throw new EntityNotFoundException("Income is not present with id "+id);
		}
	}

	@Override
	public IncomeDTO getIncomeById(Long id) {
		Optional<Income> optional = incomeRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get().getIncomeDTO();
		}
		else {
			throw new EntityNotFoundException("Income is not present with id "+id);
		}
	}

	@Override
	public void deleteIncome(Long id) {
		Optional<Income> optional = incomeRepository.findById(id);
		if(optional.isPresent()) {
			incomeRepository.deleteById(id);
		}
		else {
			throw new EntityNotFoundException("Income is not present with id "+id);
		}
	}

}
