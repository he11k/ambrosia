package rip.ambrosia.util.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;
import net.minecraft.util.Util;

import java.util.function.Function;

public class AmbrosiaRenderLayers {
    private static final RenderPhase.Transparency JCEF_COMPATIBLE_BLEND = new RenderPhase.Transparency("jcef_compatible_blend", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    });
    private static final Function<Identifier, RenderLayer> BLURRED_TEXTURE_LAYER = Util.memoize(
            textureId ->
                    RenderLayer.of(
                            "blurred_ui_layer",
                            VertexFormats.POSITION_TEXTURE_COLOR,
                            VertexFormat.DrawMode.QUADS,
                            786432,
                            RenderLayer.MultiPhaseParameters.builder()
                                    .texture(new RenderPhase.Texture(textureId, TriState.FALSE, false))
                                    .program(RenderPhase.POSITION_TEXTURE_COLOR_PROGRAM)
                                    .transparency(JCEF_COMPATIBLE_BLEND)
                                    .depthTest(RenderPhase.LEQUAL_DEPTH_TEST)
                                    .build(false)
                    ));

    public static RenderLayer getBlurredTextureLayer(Identifier textureId) {
        return BLURRED_TEXTURE_LAYER.apply(textureId);
    }
}
