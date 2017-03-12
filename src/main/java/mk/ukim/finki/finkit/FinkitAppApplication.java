package mk.ukim.finki.finkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = FinkitAppApplication.class)
public class FinkitAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinkitAppApplication.class, args);
	}
}
