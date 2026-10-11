package de.felix.nochat.mixin;

import com.mojang.authlib.GameProfile;
import de.felix.nochat.config.ChatPolicy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.PlayerChatMessage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.Instant;
import java.util.UUID;

@Mixin(ChatListener.class)
abstract class ChatListenerMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@Inject(method = "showMessageToPlayer", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$hidePlayerChat(
		ChatType.Bound chatType,
		PlayerChatMessage message,
		Component decoratedContent,
		GameProfile sender,
		boolean onlyShowSecureChat,
		Instant receivedAt,
		CallbackInfoReturnable<Boolean> cir
	) {
		if (!ChatPolicy.isChatEnabled()) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "handleChatMessageError", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$hideChatError(
		UUID sender,
		MessageSignature signature,
		ChatType.Bound chatType,
		CallbackInfo ci
	) {
		if (!ChatPolicy.isChatEnabled()) {
			ClientPacketListener connection = minecraft.getConnection();
			if (connection != null && signature != null) {
				connection.markMessageAsProcessed(signature, false);
			}
			ci.cancel();
		}
	}

	@Inject(method = "handleDisguisedChatMessage", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$hideDisguisedChat(Component message, ChatType.Bound chatType, CallbackInfo ci) {
		if (!ChatPolicy.isChatEnabled()) {
			ci.cancel();
		}
	}

	@Inject(method = "handleSystemMessage", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$hideSystemChat(Component message, boolean serverMessage, CallbackInfo ci) {
		if (!ChatPolicy.isChatEnabled()) {
			ci.cancel();
		}
	}

	@Inject(method = "handleOverlay", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$hideChatOverlay(Component message, CallbackInfo ci) {
		if (!ChatPolicy.isChatEnabled()) {
			ci.cancel();
		}
	}
}
