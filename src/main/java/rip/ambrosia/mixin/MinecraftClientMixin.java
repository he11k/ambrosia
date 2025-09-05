package rip.ambrosia.mixin;

import es.boffmedia.mcef.MCEF;
import es.boffmedia.mcef.internal.MCEFDownloadListener;
import es.boffmedia.mcef.internal.MCEFDownloaderMenu;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rip.ambrosia.Ambrosia;
import rip.ambrosia.browser.BrowserManager;
import rip.ambrosia.config.ConfigProvider;
import rip.ambrosia.event.FramebufferBlitEvent;
import rip.ambrosia.event.LevelTickEvent;
import rip.ambrosia.event.TickEvent;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;endWrite()V"))
    private void render(boolean tick, CallbackInfo callbackInfo) {
        Ambrosia.INSTANCE.getEventBus().post(new FramebufferBlitEvent());
    }
    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tick(CallbackInfo ci) {
        Ambrosia.INSTANCE.getEventBus().post(new TickEvent());
        if(MinecraftClient.getInstance().world != null){
            Ambrosia.INSTANCE.getEventBus().post(new LevelTickEvent());
        }
    }
    @Inject(method = "stop", at = @At(value = "HEAD"))
    private void stop(CallbackInfo ci) {
        BrowserManager.INSTANCE.stop();
    }



//    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setVsync(Z)V"))
//    private void onInit(RunArgs args, CallbackInfo ci) {
//        FolderMover.INSTANCE.moveFolderFromResources(Identifier.of("ambrosia", "browser"), ConfigProvider.INSTANCE.getWebFolder());
//    }
    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    @Inject(at = @At("HEAD"), method = "setScreen", cancellable = true)
    public void redirScreen(Screen screen, CallbackInfo ci) {
        System.out.println("MCEF.isInitialized(): " + MCEF.isInitialized());
        if (!MCEF.isInitialized()) {
            if (screen instanceof TitleScreen) {
                if (MCEFDownloadListener.INSTANCE.isDone() && !MCEFDownloadListener.INSTANCE.isFailed()) {
                    MinecraftClient.getInstance().execute((() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MCEF.initialize();
                    }));
                }
                // If the download is not done and didn't fail
                else if (!MCEFDownloadListener.INSTANCE.isDone() && !MCEFDownloadListener.INSTANCE.isFailed()) {
                    setScreen(new MCEFDownloaderMenu((TitleScreen) screen));
                    ci.cancel();
                }
                // If the download failed
                else if (MCEFDownloadListener.INSTANCE.isFailed()) {
                    MCEF.getLogger().error("MCEF failed to initialize!");
                }
            }
        } else {
            BrowserManager.INSTANCE.init();
        }
    }

}
