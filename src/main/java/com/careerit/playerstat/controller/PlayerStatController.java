package com.careerit.playerstat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerit.playerstat.dto.RoleAmountAndCountDto;
import com.careerit.playerstat.dto.RoleAndAmountDto;
import com.careerit.playerstat.dto.TeamAmountDto;
import com.careerit.playerstat.service.PlayerStatService;

@RestController
@RequestMapping("/api/player/stat")
public class PlayerStatController {

	@Autowired
	private PlayerStatService playerStatService;

	@GetMapping("/teamamount")
	public ResponseEntity<List<TeamAmountDto>> getAmountSpentByTeams() {
		List<TeamAmountDto> list = playerStatService.getAmountSpentByTeams();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/teamroleamount")
	public ResponseEntity<List<RoleAndAmountDto>> getAmountSpentByRole() {
		List<RoleAndAmountDto> list = playerStatService.getAmountSpentByRole();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/roleamountcount/{teamName}")
	public ResponseEntity<List<RoleAmountAndCountDto>> getCountAndAmountBy(@PathVariable("teamName") String teamName) {
		List<RoleAmountAndCountDto> list = playerStatService.getCountAndAmountBy(teamName);
		return ResponseEntity.ok(list);
	}

}
