package swt6;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.shell.jline.PromptProvider;

import java.util.concurrent.Executor;

@EnableScheduling
@EnableAsync
@SpringBootApplication // enables componentscan in this package
public class SpringBootMain implements AsyncConfigurer {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringBootMain.class);
        application.setBannerMode(Banner.Mode.OFF);
        System.out.println("===================================================");
        System.out.println("        Typ commands for available commands        ");
        System.out.println("===================================================");
        application.run(args);
    }

    @Bean("promptProvider")
    public PromptProvider getPrompt() {
        return () ->
                new AttributedString("\n> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix( "ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
