package com.sb.controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.exception.ResourceNotFoundException;
import com.sb.model.Team;
import com.sb.repository.TeamRepository;
@RestController
@RequestMapping("/api")
public class TeamController {
	
	@Autowired
	  TeamRepository teamRepository;

	  @GetMapping("/teams")
	  public ResponseEntity<List<Team>> getAllTeams(@RequestParam(required = false) String title) {
	    List<Team> teams = new ArrayList<Team>();

	    if (title == null)
	      teamRepository.findAll().forEach(teams::add);
	    else
	      teamRepository.findByTitleContaining(title).forEach(teams::add);

	    if (teams.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    return new ResponseEntity<>(teams, HttpStatus.OK);
	  }

	  @GetMapping("/teams/{id}")
	  public ResponseEntity<Team> getTutorialById(@PathVariable("id") long id) throws ResourceNotFoundException {
	    Team team = teamRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

	    return new ResponseEntity<>(team, HttpStatus.OK);
	  }

	  @PostMapping("/teams")
	  public ResponseEntity<Team> createTutorial(@RequestBody Team team) {
	    Team team1 = teamRepository.save(new Team(team.getTitle(), team.getDescription(), true));
	    return new ResponseEntity<>(team1, HttpStatus.CREATED);
	  }

	  

	  @DeleteMapping("/teams/{id}")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
	    teamRepository.deleteById(id);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

	  @DeleteMapping("/teams")
	  public ResponseEntity<HttpStatus> deleteAllTutorials() {
	    teamRepository.deleteAll();
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

	  @GetMapping("/teams/published")
	  public ResponseEntity<List<Team>> findByPublished() {
	    List<Team> tutorials = teamRepository.findByPublished(true);

	    if (tutorials.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    
	    return new ResponseEntity<>(tutorials, HttpStatus.OK);}
	}


