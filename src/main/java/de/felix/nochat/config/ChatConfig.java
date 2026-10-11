package de.felix.nochat.config;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ChatConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger("FelixNoChat");
	private static final String RESOURCE_NAME = "/config.yml";
	private static final Path CONFIG_PATH = FabricLoader.getInstance()
		.getConfigDir()
		.resolve("felix-no-chat")
		.resolve("config.yml");

	private static final ChatConfig INSTANCE = load();

	private final boolean defaultEnabled;
	private final List<String> allowedServers;

	private ChatConfig(boolean defaultEnabled, List<String> allowedServers) {
		this.defaultEnabled = defaultEnabled;
		this.allowedServers = List.copyOf(allowedServers);
	}

	public static ChatConfig get() {
		return INSTANCE;
	}

	public boolean isChatEnabledFor(String host) {
		if (defaultEnabled) {
			return true;
		}

		String normalizedHost = normalizeHost(host);
		if (normalizedHost.isEmpty()) {
			return false;
		}

		for (String allowedServer : allowedServers) {
			if (matches(normalizedHost, allowedServer)) {
				return true;
			}
		}
		return false;
	}

	static boolean matches(String host, String rule) {
		if (rule.startsWith("*.")) {
			String suffix = rule.substring(1);
			return host.endsWith(suffix) && host.length() > suffix.length();
		}
		return host.equals(rule);
	}

	private static ChatConfig load() {
		try {
			installDefaultConfig();
			return parse(Files.readAllLines(CONFIG_PATH, StandardCharsets.UTF_8));
		} catch (Exception exception) {
			LOGGER.error("Could not load {}. Chat will remain disabled.", CONFIG_PATH, exception);
			return new ChatConfig(false, List.of());
		}
	}

	private static void installDefaultConfig() throws IOException {
		if (Files.exists(CONFIG_PATH)) {
			return;
		}

		Files.createDirectories(CONFIG_PATH.getParent());
		try (InputStream input = ChatConfig.class.getResourceAsStream(RESOURCE_NAME)) {
			if (input == null) {
				throw new IOException("Missing bundled " + RESOURCE_NAME);
			}
			Files.copy(input, CONFIG_PATH);
		}
	}

	private static ChatConfig parse(List<String> lines) {
		boolean inChat = false;
		boolean inAllowedServers = false;
		boolean defaultEnabled = false;
		List<String> allowedServers = new ArrayList<>();

		for (String rawLine : lines) {
			String line = stripComment(rawLine).trim();
			if (line.isEmpty()) {
				continue;
			}
			if (line.equals("chat:")) {
				inChat = true;
				inAllowedServers = false;
				continue;
			}
			if (!inChat) {
				continue;
			}
			if (line.startsWith("default-enabled:")) {
				String value = line.substring("default-enabled:".length()).trim();
				if (!value.equals("true") && !value.equals("false")) {
					throw new IllegalArgumentException("default-enabled must be true or false");
				}
				defaultEnabled = Boolean.parseBoolean(value);
				inAllowedServers = false;
				continue;
			}
			if (line.equals("allowed-servers:")) {
				inAllowedServers = true;
				continue;
			}
			if (inAllowedServers && line.startsWith("-")) {
				String rule = normalizeHost(unquote(line.substring(1).trim()));
				if (!rule.isEmpty()) {
					allowedServers.add(rule);
				}
			}
		}

		return new ChatConfig(defaultEnabled, allowedServers);
	}

	private static String normalizeHost(String value) {
		String host = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
		if (host.endsWith(".")) {
			host = host.substring(0, host.length() - 1);
		}
		return host;
	}

	private static String unquote(String value) {
		if (value.length() >= 2) {
			char first = value.charAt(0);
			char last = value.charAt(value.length() - 1);
			if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
				return value.substring(1, value.length() - 1);
			}
		}
		return value;
	}

	private static String stripComment(String value) {
		boolean singleQuoted = false;
		boolean doubleQuoted = false;
		for (int index = 0; index < value.length(); index++) {
			char character = value.charAt(index);
			if (character == '\'' && !doubleQuoted) {
				singleQuoted = !singleQuoted;
			} else if (character == '"' && !singleQuoted) {
				doubleQuoted = !doubleQuoted;
			} else if (character == '#' && !singleQuoted && !doubleQuoted) {
				return value.substring(0, index);
			}
		}
		return value;
	}
}
