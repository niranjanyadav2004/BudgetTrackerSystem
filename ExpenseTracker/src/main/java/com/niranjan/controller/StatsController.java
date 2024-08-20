package com.niranjan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niranjan.DTO.GraphDTO;
import com.niranjan.service.StatsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StatsController {

	@Autowired
	private final StatsService service;
	
	@GetMapping("/chart")
	public ResponseEntity<GraphDTO> getChartData(){
		return ResponseEntity.ok(service.getChartData());
	}
	
	@GetMapping
	public ResponseEntity<?> getStats(){
		return ResponseEntity.ok(service.getStats());
	}
	
}
