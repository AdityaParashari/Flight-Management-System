package com.capg.flightmanagement.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.flightmanagement.dto.ScheduleRequest;
import com.capg.flightmanagement.exceptions.ScheduleNotFoundException;
import com.capg.flightmanagement.models.Schedule;
import com.capg.flightmanagement.service.IScheduleService;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

	private static final Logger Log = LoggerFactory.getLogger(ScheduleController.class);
	
	@Autowired
	private IScheduleService scheduleService;
	
	
	/**
	 * adding schedule and return scheduleDetails
	 * @Param ScheduleRequest
	 * @return
	 */
	@PostMapping("/add")
	ResponseEntity<Schedule> addingSchedule(@RequestBody @Valid ScheduleRequest scheduleDto){
		Schedule schedule = convertScheduleDto(scheduleDto);
		schedule=scheduleService.addSchedule(schedule);
		ResponseEntity<Schedule> response = new ResponseEntity<Schedule>(schedule,HttpStatus.OK);
		return response;
	}
	
	/**
	 * Fetching schedule and scheduleDetails
	 * @param airportCode
	 * @return
	 */
	@GetMapping("/getSchedule/{id}")
	ResponseEntity<Schedule> fetchSchedule(@PathVariable("id") String scheduleId){
		Schedule schedule = scheduleService.fetchScheduleById(scheduleId);
		ResponseEntity<Schedule> response = new ResponseEntity<Schedule>(schedule,HttpStatus.OK);
		return response;
	}
	
	/**
	 * Fetching all schedules
	 * @return
	 */
	@GetMapping
	ResponseEntity<List<Schedule>> fetchAllSchedules(){
		List<Schedule> schedules = scheduleService.fetchAllSchedules();
		ResponseEntity<List<Schedule>> response = new ResponseEntity<List<Schedule>>(schedules,HttpStatus.OK);
		return response;
	}
	
	/**
	 * Updating schedule
	 * @param scheduleId and scheduleDto
	 * @return
	 */
	@PutMapping("/updateSchedule/{id}")
	ResponseEntity<Schedule> updateSchedule(@PathVariable("id") String scheduleId,@RequestBody ScheduleRequest scheduleDto){
		Schedule schedule = new Schedule(scheduleId,scheduleDto.getSourceAirport(),scheduleDto.getDestinationAirport(),LocalDateTime.parse(scheduleDto.getArrivalTime()),LocalDateTime.parse(scheduleDto.getDepartureTime()));
		Schedule resp=scheduleService.updateSchedule(schedule);
		ResponseEntity<Schedule> response = new ResponseEntity<Schedule>(resp,HttpStatus.OK);
		return response;
	}
	
	/**
	 * Deleting schedule
	 * @param scheduleId
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	ResponseEntity<Schedule> deleteSchedule(@PathVariable("id") String scheduleId){
		Schedule schedule=scheduleService.removeSchedule(scheduleId);
		ResponseEntity<Schedule> response = new ResponseEntity<Schedule>(schedule,HttpStatus.OK);
		return response;
	}
	
	/**
	 * Converting ScheduleRequest to Schedule
	 * @param scheduleDto
	 * @return
	*/
	public Schedule convertScheduleDto(ScheduleRequest scheduleDto) {
		Schedule schedule = new Schedule(scheduleDto.getScheduleId(),scheduleDto.getSourceAirport(),scheduleDto.getDestinationAirport(),LocalDateTime.parse(scheduleDto.getArrivalTime()),LocalDateTime.parse(scheduleDto.getDepartureTime()));
		return schedule;
	}
	
	
}
