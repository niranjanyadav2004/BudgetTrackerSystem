package com.niranjan.service;

import com.niranjan.DTO.GraphDTO;
import com.niranjan.DTO.StatsDTO;

public interface StatsService {

	GraphDTO getChartData();
	
	StatsDTO getStats();
	
}
