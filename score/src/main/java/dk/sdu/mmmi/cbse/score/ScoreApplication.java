package dk.sdu.mmmi.cbse.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoreApplication {
  
	private static long highScore = 0;
	private static long currentScore = 0;

	public static void main(String[] args) {
		SpringApplication.run(ScoreApplication.class, args);
	}

	@GetMapping("/score")
	public long getScore() {
		return currentScore;
	}

	@PostMapping("/score")
	public long addPoints(@RequestParam(value = "point", defaultValue = "0") long point) {
		currentScore = currentScore + point;
		if (currentScore > highScore) {
			highScore = currentScore;
		}
		return currentScore;
	}

	@GetMapping("/highscore")
	public long getHighScore() {
		return highScore;
	}

	@PostMapping("/reset")
	public long resetPoints(long point) {
		currentScore = 0;
		return currentScore;
	}
}