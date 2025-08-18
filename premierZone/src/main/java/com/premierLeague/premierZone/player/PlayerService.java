package com.premierLeague.premierZone.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<player> getPlayers() {
        return playerRepository.findAll();
    }

    public List<player> getPlayersFromTeam(String teamName) {
        return playerRepository.findAll().stream().filter(player -> teamName.equals(player.getTeam())).collect(Collectors.toList());
    }

    public List<player> getPlayersByName(String searchText) {
        return playerRepository.findAll().stream().filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase())).collect(Collectors.toList());
    }

    public List<player> getPlayersByPos(String searchText) {
        return playerRepository.findAll().stream().filter(player -> player.getPos().toLowerCase().contains(searchText.toLowerCase())).collect(Collectors.toList());
    }

    public List<player> getPlayersByNation(String searchText) {
        return playerRepository.findAll().stream().filter(player -> player.getNation().toLowerCase().contains(searchText.toLowerCase())).collect(Collectors.toList());
    }

    public List<player> getPlayersByTeamAndPosition(String team, String position) {
        return playerRepository.findAll().stream().filter(player -> team.equals(player.getTeam()) && position.equals(player.getPos())).collect(Collectors.toList());
    }

    public player addPlayer(player player) {
        playerRepository.save(player);
        return player;
    }

    public player updatePlayer(player updatedPlayer) {
        Optional<player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());
        if (existingPlayer.isPresent()) {
            player playerToUpdate = existingPlayer.get();
            playerToUpdate.setName(updatedPlayer.getName());
            playerToUpdate.setTeam(updatedPlayer.getTeam());
            playerToUpdate.setPos(updatedPlayer.getPos());
            playerToUpdate.setNation(updatedPlayer.getNation());

            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }
        return null;
    }

    @Transactional
    public void deletePlayer(String playerName) {
        playerRepository.deleteByName(playerName);
    }
}
