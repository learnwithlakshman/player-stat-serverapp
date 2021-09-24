package com.careerit.playerstat.dao;

import java.util.List;

import com.careerit.playerstat.dto.RoleAmountAndCountDto;
import com.careerit.playerstat.dto.RoleAndAmountDto;
import com.careerit.playerstat.dto.TeamAmountDto;

public interface PlayerStatDao {

	List<TeamAmountDto> findAmountSpentByTeams();
	List<RoleAndAmountDto> findAmountSpentByRole();
	List<RoleAmountAndCountDto> findCountAndAmountBy(String teamName);
	
}
