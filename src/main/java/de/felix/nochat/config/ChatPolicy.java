package de.felix.nochat.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

public final class ChatPolicy {
	private ChatPolicy() {
	}

	public static boolean isChatEnabled() {
		ServerData server = Minecraft.getInstance().getCurrentServer();
		if (server == null) {
			return ChatConfig.get().isChatEnabledFor(null);
		}

		String host = ServerAddress.parseString(server.ip).getHost();
		return ChatConfig.get().isChatEnabledFor(host);
	}
}
