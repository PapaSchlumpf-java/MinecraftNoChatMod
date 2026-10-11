package de.felix.nochat.mixin;

import de.felix.nochat.config.ChatPolicy;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
abstract class GuiMixin {
	@Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
	private void felixNoChat$blockChatScreen(Screen screen, CallbackInfo ci) {
		if (screen instanceof ChatScreen && !ChatPolicy.isChatEnabled()) {
			ci.cancel();
		}
	}
}
