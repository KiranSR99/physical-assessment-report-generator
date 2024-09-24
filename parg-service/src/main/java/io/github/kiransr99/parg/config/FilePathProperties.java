package io.github.kiransr99.parg.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "app.filepath")
public class FilePathProperties {
    private String windows;
    private String linux;
    private String mac;

}