package de.felix.nochat.mixin;

import de.felix.nochat.config.ChatPolicy;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerMixin {
	@Inject(method = "sendChat", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$blockOutgoingChat(String message, CallbackInfo ci) {
		if (!ChatPolicy.isChatEnabled()) {
			ci.cancel();
		}
	}

	@Inject(method = "sendCommand", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$blockOutgoingCommand(String command, CallbackInfo ci) {
		if (!ChatPolicy.isChatEnabled()) {
			ci.cancel();
		}
	}

	@Inject(method = "sendUnattendedCommand", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$blockUnattendedCommand(String command, Screen screen, CallbackInfo ci) {
		if (!ChatPolicy.isChatEnabled()) {
			ci.cancel();
		}
	}
}
