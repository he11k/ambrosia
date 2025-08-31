package rip.ambrosia.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rip.ambrosia.command.Command;
import rip.ambrosia.command.CommandController;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Inject(method = "sendMessage", at = @At(value = "INVOKE", target = "Ljava/lang/String;startsWith(Ljava/lang/String;)Z", shift = At.Shift.BEFORE), cancellable = true)
    public void sendMessage(String chatText, boolean addToHistory, CallbackInfo callbackInfo) {
        if (chatText.startsWith(CommandController.INSTANCE.getPrefix())) {
            if(CommandController.INSTANCE.validate(chatText)) {
                for (Command command : CommandController.INSTANCE.getCommands()) {
                    if(chatText.split(" ")[0].contains(command.getName())) {
                        command.run(chatText.split(" "));
                    }
                }
            }
            callbackInfo.cancel();
        }
    }
}
