package AI.Assignment1.config;

import AI.Assignment1.UI.MainScreen;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//@Configuration
public class Config {

    @Bean
    @Scope("prototype")
    public MainScreen mainScreen() {
        return new MainScreen();
    }
}
