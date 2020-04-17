package net.krows_team.flexmine.screens;

import com.mojang.blaze3d.systems.RenderSystem;

import net.krows_team.flexmine.FlexMine;
import net.krows_team.flexmine.containers.SpinnerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpinnerScreen extends ContainerScreen<SpinnerContainer> {
	
	private static final ResourceLocation LOCATION = new ResourceLocation(FlexMine.MOD_ID, "textures/gui/container/spinner.png");
	
	public SpinnerScreen(SpinnerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		
		super(screenContainer, inv, titleIn);
	}
	
	@Override
	public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
		
		renderBackground();
		
		super.render(p_render_1_, p_render_2_, p_render_3_);
		
		renderHoveredToolTip(p_render_1_, p_render_2_);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		minecraft.getTextureManager().bindTexture(LOCATION);
		
		int x = guiLeft;
		int y = guiTop;
		int l = container.getCookProgressionScaled();
		
		blit(x, y, 0, 0, xSize, ySize);
		blit(x + 79, y + 34, 176, 14, l + 1, 16);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
		String s = title.getFormattedText();
		
		font.drawString(s, xSize / 2 - font.getStringWidth(s) / 2, 6.0F, 4210752);
		font.drawString(playerInventory.getDisplayName().getFormattedText(), 8.0F, ySize - 94, 4210752);
	}
}