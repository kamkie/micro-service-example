package net.devopssolutions.microservice.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller that provides an API for logfiles, i.e. downloading the main logfile configured in environment property
 * 'logging.file' that is standard, but optional property for spring-boot applications.
 */
@Component
@ConfigurationProperties(prefix = "endpoints.logfile")
public class LogfileMvcEndpoint implements MvcEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogfileMvcEndpoint.class);

    @Value("${logging.file}")
    private String logfile;

    private String path = "/logfile";

    private boolean sensitive = true;

    private boolean enabled = true;

    @Override
    public boolean isSensitive() {
        return sensitive;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends Endpoint> getEndpointType() {
        return null;
    }


    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }

    public String getLogfile() {
        return logfile;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @RequestMapping(method = RequestMethod.GET)
    public void invoke(HttpServletResponse response) throws IOException {
        if (!isAvailable()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        Resource file = new FileSystemResource(logfile);
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"");
        FileCopyUtils.copy(file.getInputStream(), response.getOutputStream());
    }


    @RequestMapping(method = RequestMethod.HEAD)
    @ResponseBody
    public ResponseEntity<Object> available() {
        if (isAvailable()) {
            return new ResponseEntity<Object>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

    private boolean isAvailable() {
        if (!enabled) {
            return false;
        }

        if (logfile == null) {
            LOGGER.error("Logfile download failed for missing property 'logging.file'");
            return false;
        }

        Resource file = new FileSystemResource(logfile);
        if (!file.exists()) {
            LOGGER.error("Logfile download failed for missing file at path={}", logfile);
            return false;
        }

        return true;
    }


}
