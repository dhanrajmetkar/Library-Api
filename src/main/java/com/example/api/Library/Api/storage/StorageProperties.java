package com.example.api.Library.Api.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Data
@Component
public class StorageProperties {
     String location = "upload-dir";
}
