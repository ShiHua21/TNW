package com.jic.tnw.web.api.vo.response;

import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

/**
 * Created by lee5hx on 2017/10/17.
 */
public class IndexResource extends ResourceSupport {
    private String welcome;
    private LocalDateTime time = LocalDateTime.now();
    private String version;

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
