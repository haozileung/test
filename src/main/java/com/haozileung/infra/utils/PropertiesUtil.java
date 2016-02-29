package com.haozileung.infra.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;

public class PropertiesUtil {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Properties properties;

	public static Properties getProperties() {
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
		return properties;
	}

}
