package SeekerPoolBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SeekerPoolBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeekerPoolBootApplication.class, args);
	}

}
