package com.careerit.playerstat.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.careerit.playerstat.dto.RoleAmountAndCountDto;
import com.careerit.playerstat.dto.RoleAndAmountDto;
import com.careerit.playerstat.dto.TeamAmountDto;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PlayerStatDaoImpl implements PlayerStatDao {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<TeamAmountDto> findAmountSpentByTeams() {
		
		AggregationOperation group = Aggregation.group("team").sum("price").as("totalAmount");
		AggregationOperation project = Aggregation.project().andExclude("_id").and("_id").as("teamName").andInclude("totalAmount");
		Aggregation aggregation = Aggregation.newAggregation(group, project);
	
		AggregationResults<TeamAmountDto> res = mongoTemplate.aggregate(aggregation, "player", TeamAmountDto.class);
		List<TeamAmountDto> teamAmountList = res.getMappedResults();
		log.info("Total teams count is :{}", teamAmountList.size());
		return teamAmountList;
		
	}

	@Override
	public List<RoleAndAmountDto> findAmountSpentByRole() {
		AggregationOperation group = Aggregation.group("role").sum("price").as("totalAmount");
		AggregationOperation project = Aggregation.project().andExclude("_id").and("_id").as("roleName").andInclude("totalAmount");
		Aggregation aggregation = Aggregation.newAggregation(group, project);
		AggregationResults<RoleAndAmountDto> res = mongoTemplate.aggregate(aggregation, "player", RoleAndAmountDto.class);
		List<RoleAndAmountDto> roleCountList = res.getMappedResults();
		log.info("Total role count is :{}", roleCountList.size());
		return roleCountList;
	}

	@Override
	public List<RoleAmountAndCountDto> findCountAndAmountBy(String teamName) {
		
		AggregationOperation match = Aggregation.match(Criteria.where("team").is(teamName));
		AggregationOperation group = Aggregation.group("role").sum("price").as("totalAmount").count().as("totalCount");
		AggregationOperation project = Aggregation.project().andExclude("_id").and("_id").as("roleName").andInclude("totalAmount","totalCount");
		Aggregation aggregation = Aggregation.newAggregation(match,group, project);
		AggregationResults<RoleAmountAndCountDto> res = mongoTemplate.aggregate(aggregation, "player", RoleAmountAndCountDto.class);
		List<RoleAmountAndCountDto> roleCountList = res.getMappedResults();
		log.info("Team {} has total role count :{}",teamName,roleCountList.size());
		return roleCountList;
	}

}
