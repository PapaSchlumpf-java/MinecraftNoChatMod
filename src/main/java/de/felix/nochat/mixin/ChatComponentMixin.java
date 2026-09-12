package de.felix.nochat.mixin;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessageSource;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
abstract class ChatComponentMixin {
	@Inject(method = "addMessage", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$hideIncomingMessages(
		Component message,
		MessageSignature signature,
		GuiMessageSource source,
		GuiMessageTag tag,
		CallbackInfo ci
	) {
		ci.cancel();
	}
}
