package com.careerit.playerstat.batch;

import org.springframework.batch.item.ItemProcessor;

import com.careerit.playerstat.domain.Player;

public class PlayerProcessor implements ItemProcessor<PlayerDTO,Player> {

	@Override
	public Player process(PlayerDTO dto) throws Exception {
		
		Player player = Player.builder()
				              .name(dto.getName())
				              .price(Double.parseDouble(dto.getPrice()))
				              .country(dto.getCountry())
				              .role(dto.getRole())
				              .team(dto.getTeam()).build();
		return player;
	}

}
