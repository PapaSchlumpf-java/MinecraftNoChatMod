package de.felix.nochat.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerMixin {
	@Inject(method = "sendChat", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$blockOutgoingChat(String message, CallbackInfo ci) {
		ci.cancel();
	}
}
