package com.careerit.playerstat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.careerit.playerstat.dao.PlayerStatDao;
import com.careerit.playerstat.dto.RoleAmountAndCountDto;
import com.careerit.playerstat.dto.RoleAndAmountDto;
import com.careerit.playerstat.dto.TeamAmountDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlayerStatServiceImpl implements PlayerStatService {

	@Autowired
	private PlayerStatDao playerStatDao;

	@Override
	public List<TeamAmountDto> getAmountSpentByTeams() {
		List<TeamAmountDto> list = playerStatDao.findAmountSpentByTeams();
		log.info("Team count is :{}", list.size());
		return list;
	}

	@Override
	public List<RoleAndAmountDto> getAmountSpentByRole() {
		return playerStatDao.findAmountSpentByRole();
	}

	@Override
	public List<RoleAmountAndCountDto> getCountAndAmountBy(String teamName) {
		return playerStatDao.findCountAndAmountBy(teamName);
	}

}
