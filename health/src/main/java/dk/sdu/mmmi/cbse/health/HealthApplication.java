package dk.sdu.mmmi.cbse.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HealthApplication {

	private static long health = 1;

	public static void main(String[] args) {
		SpringApplication.run(HealthApplication.class, args);
	}

	@GetMapping("/health")
	public long getHealth() {
		return health;
	}

	@PostMapping("/health")
	public long setHealth(@RequestParam(value = "amount", defaultValue = "0") long amount) {
		health = amount;
		return health;
	}
}
