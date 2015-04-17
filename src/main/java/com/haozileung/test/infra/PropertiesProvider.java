package com.haozileung.test.infra;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesProvider {
    private static final Logger logger = LoggerFactory
            .getLogger(PropertiesProvider.class);
    private static Properties properties;

    public static void init() {
        if (null == properties) {
            properties = new Properties();
            final URL url = Resources.getResource("app.properties");
            final ByteSource byteSource = Resources.asByteSource(url);
            InputStream inputStream = null;
            try {
                inputStream = byteSource.openBufferedStream();
                properties.load(inputStream);
            } catch (final IOException ioException) {
                logger.error(ioException.getMessage(), ioException);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (final IOException ioException) {
                        logger.error(ioException.getMessage(), ioException);
                    }
                }
            }
        }
    }

    public static void destroy() {
        properties.clear();
        properties = null;
    }

    public static Properties getProperties() {
        return properties;
    }

}
