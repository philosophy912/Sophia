package com.chinatsp.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author lizhe
 */
@SpringBootApplication(scanBasePackages = {"com.philosophy", "com.chinatsp"})
@Slf4j
public class ToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsApplication.class, args);
    }

}
