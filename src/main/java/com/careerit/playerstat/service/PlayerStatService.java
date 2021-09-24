package com.careerit.playerstat.service;

import java.util.List;

import com.careerit.playerstat.dto.RoleAmountAndCountDto;
import com.careerit.playerstat.dto.RoleAndAmountDto;
import com.careerit.playerstat.dto.TeamAmountDto;

public interface PlayerStatService {

		List<TeamAmountDto> getAmountSpentByTeams();
		List<RoleAndAmountDto> getAmountSpentByRole();
		List<RoleAmountAndCountDto> getCountAndAmountBy(String teamName);
		
}
