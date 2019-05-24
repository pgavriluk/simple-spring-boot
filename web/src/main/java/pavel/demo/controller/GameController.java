package pavel.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import pavel.demo.service.GameService;
import pavel.demo.util.AttributeNames;
import pavel.demo.util.GameMappings;
import pavel.demo.util.ViewNames;

@Slf4j
@Controller
public class GameController {

	// fields
	private final GameService gameService;

	// constructor

	@Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	// request methods
	@GetMapping(GameMappings.PLAY)
	public String play(Model model) {
		model.addAttribute(AttributeNames.MAIN_MESSAGE, gameService.getMainMessage());
		model.addAttribute(AttributeNames.RESULT_MESSAGE, gameService.getResultMessage());
		log.info("model is = {}", model);
		if (gameService.isGameOver()) {
			gameService.reset();
			return ViewNames.GAME_OVER;
		}

		return ViewNames.PLAY;
	}

	@PostMapping(GameMappings.PLAY)
	public String processMessage(@RequestParam int guess) {
		log.info("guess is = {}", guess);
		gameService.checkGuess(guess);
		return GameMappings.REDIRECT_PLAY;
	}

	@GetMapping(GameMappings.RESTART)
	public String restart(){
		gameService.reset();
		return GameMappings.REDIRECT_PLAY;
	}
}
