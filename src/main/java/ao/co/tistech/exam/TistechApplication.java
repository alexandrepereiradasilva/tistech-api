package ao.co.tistech.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCaching
public class TistechApplication {

    public static void main(String[] args) {
        SpringApplication.run(TistechApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "Welcome - API of Tistech";
    }
}
