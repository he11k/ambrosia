package es.boffmedia.mcef.internal;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class MCEFDownloaderMenu extends Screen {
    private final TitleScreen menu;

    public MCEFDownloaderMenu(TitleScreen menu) {
        super(Text.literal("MCEF is downloading required libraries..."));
        this.menu = menu;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        double cx = width / 2d;
        double cy = height / 2d;

        double progressBarHeight = 14;
        double progressBarWidth = width / 3d;
        MatrixStack stack = context.getMatrices();
        stack.push();
        stack.translate(cx, cy, 0);
        stack.translate(-progressBarWidth / 2d, -progressBarHeight / 2d, 0);
        context.fill(0, 0, (int) progressBarWidth, (int) progressBarHeight, -1);
        context.fill(2, 2, (int) progressBarWidth - 2, (int) progressBarHeight - 2, -16777215);
        context.fill(4, 4, (int) ((progressBarWidth - 4) * MCEFDownloadListener.INSTANCE.getProgress()), (int) progressBarHeight - 4, -1);
        stack.pop();
        String[] text = new String[]{
                MCEFDownloadListener.INSTANCE.getTask(),
                Math.round(MCEFDownloadListener.INSTANCE.getProgress() * 100) + "%",
        };

        int oSet = ((textRenderer.fontHeight / 2) + ((textRenderer.fontHeight + 2) * (text.length + 2))) + 4;
        stack.push();
        stack.translate(
                (int) (cx),
                (int) (cy - oSet),
                0
        );
        context.drawText(textRenderer, title.getString(), (int) -(textRenderer.getWidth(title.getString()) / 2d), 0, 0xFFFFFF, false);

        int index = 0;
        for (String s : text) {
            if (index == 1) {
                stack.translate(0, textRenderer.fontHeight + 2, 0);
            }

            stack.translate(0, textRenderer.fontHeight + 2, 0);
            context.drawText(textRenderer, s,
                    (int) -(textRenderer.getWidth(s) / 2d), 0,
                    0xFFFFFF, false);

            index++;
        }
        stack.pop();
    }

    @Override
    public void tick() {
        if (MCEFDownloadListener.INSTANCE.isDone() || MCEFDownloadListener.INSTANCE.isFailed()) {
            close();
            MinecraftClient.getInstance().setScreen(menu);
        }
    }

}