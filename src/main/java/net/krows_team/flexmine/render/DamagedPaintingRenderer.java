package net.krows_team.flexmine.render;

import net.krows_team.flexmine.entities.DamagedPaintingEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DamagedPaintingRenderer extends EntityRenderer<DamagedPaintingEntity> {

	public DamagedPaintingRenderer(EntityRendererManager renderManagerIn) {
		
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getEntityTexture(DamagedPaintingEntity entity) {
		
		return null;
	}
	
//	@Override
//	public void func_225623_a_(DamagedPaintingEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
//		
//		super.func_225623_a_(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
//		
//		RenderSystem.pushMatrix();
//		RenderSystem.translated(x, y, z);
//		RenderSystem.rotatef(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
//		RenderSystem.enableRescaleNormal();
//		RenderSystem.scalef(0.0625F, 0.0625F, 0.0625F);
//		
//		if(renderOutlines) {
//			
//			RenderSystem.enableColorMaterial();
//			RenderSystem.setupSolidRenderingTextureCombine(getTeamColor(entity));
//		}
//		
//		PaintingSpriteUploader uploader = Minecraft.getInstance().getPaintingSpriteUploader();
//		
////		func_217762_a(entity, entity.art.getWidth(), entity.art.getHeight(), Minecraft.getInstance().textureManager.getTexture(new ResourceLocation(MyLocalMod.MOD_ID, "item.damaged_painting_mask.png")), uploader.func_215286_b());
//		
////		Reflections.invoke(this, "func_217762_a", new Object[] {entity, entity.art.getWidth(), entity.art.getHeight(), Minecraft.getInstance().textureManager.getTexture(new ResourceLocation(MyLocalMod.MOD_ID, "item.damaged_painting_mask.png")), uploader.func_215286_b()});
//		
//		if(renderOutlines) {
//			
////			RenderSystem.tearDownSolidRenderingTextureCombine();
//			RenderSystem.teardownOutline();
//			RenderSystem.disableColorMaterial();
//		}
//		
//		RenderSystem.disableRescaleNormal();
//		RenderSystem.popMatrix();
//	}
	
//	@Override
//	public void doRender(DamagedPaintingEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
//		
//		super.doRender(entity, x, y, z, entityYaw, partialTicks);
//		
//		GlStateManager.pushMatrix();
//		GlStateManager.translated(x, y, z);
//		GlStateManager.rotatef(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
//		GlStateManager.enableRescaleNormal();
//		GlStateManager.scalef(0.0625F, 0.0625F, 0.0625F);
//		
//		if(renderOutlines) {
//			
//			GlStateManager.enableColorMaterial();
//			GlStateManager.setupSolidRenderingTextureCombine(getTeamColor(entity));
//		}
//		
//		PaintingSpriteUploader uploader = Minecraft.getInstance().getPaintingSpriteUploader();
//		
//		Logger.getGlobal().info("Texture: " + Minecraft.getInstance().getTextureManager().getTexture(new ResourceLocation(MyLocalMod.MOD_ID, "textures.item.damaged_painting_mask.png")));
//		
////		func_217762_a(entity, entity.art.getWidth(), entity.art.getHeight(), Minecraft.getInstance().textureManager.getTexture(new ResourceLocation(MyLocalMod.MOD_ID, "item.damaged_painting_mask.png")), uploader.func_215286_b());
//		
////		Reflections.invoke(this, "func_217762_a", new Object[] {entity, entity.art.getWidth(), entity.art.getHeight(), Minecraft.getInstance().textureManager.getTexture(new ResourceLocation(MyLocalMod.MOD_ID, "item.damaged_painting_mask.png")), uploader.func_215286_b()});
//		
//		if(renderOutlines) {
//			
//			GlStateManager.tearDownSolidRenderingTextureCombine();
//			GlStateManager.disableColorMaterial();
//		}
//		
//		GlStateManager.disableRescaleNormal();
//		GlStateManager.popMatrix();
//	}

//	@Override
//	protected ResourceLocation getEntityTexture(DamagedPaintingEntity entity) {
//		
//		return AtlasTexture.LOCATION_PAINTINGS_TEXTURE;
//	}
//	
//	private void func_217762_a(DamagedPaintingEntity p_217762_1_, int p_217762_2_, int p_217762_3_, TextureAtlasSprite p_217762_4_, TextureAtlasSprite p_217762_5_) {
//	      float f = (float)(-p_217762_2_) / 2.0F;
//	      float f1 = (float)(-p_217762_3_) / 2.0F;
//	      float f2 = 0.5F;
//	      float f3 = p_217762_5_.getMinU();
//	      float f4 = p_217762_5_.getMaxU();
//	      float f5 = p_217762_5_.getMinV();
//	      float f6 = p_217762_5_.getMaxV();
//	      float f7 = p_217762_5_.getMinU();
//	      float f8 = p_217762_5_.getMaxU();
//	      float f9 = p_217762_5_.getMinV();
//	      float f10 = p_217762_5_.getInterpolatedV(1.0D);
//	      float f11 = p_217762_5_.getMinU();
//	      float f12 = p_217762_5_.getInterpolatedU(1.0D);
//	      float f13 = p_217762_5_.getMinV();
//	      float f14 = p_217762_5_.getMaxV();
//	      int i = p_217762_2_ / 16;
//	      int j = p_217762_3_ / 16;
//	      double d0 = 16.0D / (double)i;
//	      double d1 = 16.0D / (double)j;
//
//	      for(int k = 0; k < i; ++k) {
//	         for(int l = 0; l < j; ++l) {
//	            float f15 = f + (float)((k + 1) * 16);
//	            float f16 = f + (float)(k * 16);
//	            float f17 = f1 + (float)((l + 1) * 16);
//	            float f18 = f1 + (float)(l * 16);
//	            this.setLightmap(p_217762_1_, (f15 + f16) / 2.0F, (f17 + f18) / 2.0F);
//	            float f19 = p_217762_4_.getInterpolatedU(d0 * (double)(i - k));
//	            float f20 = p_217762_4_.getInterpolatedU(d0 * (double)(i - (k + 1)));
//	            float f21 = p_217762_4_.getInterpolatedV(d1 * (double)(j - l));
//	            float f22 = p_217762_4_.getInterpolatedV(d1 * (double)(j - (l + 1)));
//	            Tessellator tessellator = Tessellator.getInstance();
//	            BufferBuilder bufferbuilder = tessellator.getBuffer();
//	            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
//	            bufferbuilder.pos((double)f15, (double)f18, -0.5D).tex((double)f20, (double)f21).normal(0.0F, 0.0F, -1.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f18, -0.5D).tex((double)f19, (double)f21).normal(0.0F, 0.0F, -1.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f17, -0.5D).tex((double)f19, (double)f22).normal(0.0F, 0.0F, -1.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f17, -0.5D).tex((double)f20, (double)f22).normal(0.0F, 0.0F, -1.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f17, 0.5D).tex((double)f3, (double)f5).normal(0.0F, 0.0F, 1.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f17, 0.5D).tex((double)f4, (double)f5).normal(0.0F, 0.0F, 1.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f18, 0.5D).tex((double)f4, (double)f6).normal(0.0F, 0.0F, 1.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f18, 0.5D).tex((double)f3, (double)f6).normal(0.0F, 0.0F, 1.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f17, -0.5D).tex((double)f7, (double)f9).normal(0.0F, 1.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f17, -0.5D).tex((double)f8, (double)f9).normal(0.0F, 1.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f17, 0.5D).tex((double)f8, (double)f10).normal(0.0F, 1.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f17, 0.5D).tex((double)f7, (double)f10).normal(0.0F, 1.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f18, 0.5D).tex((double)f7, (double)f9).normal(0.0F, -1.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f18, 0.5D).tex((double)f8, (double)f9).normal(0.0F, -1.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f18, -0.5D).tex((double)f8, (double)f10).normal(0.0F, -1.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f18, -0.5D).tex((double)f7, (double)f10).normal(0.0F, -1.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f17, 0.5D).tex((double)f12, (double)f13).normal(-1.0F, 0.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f18, 0.5D).tex((double)f12, (double)f14).normal(-1.0F, 0.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f18, -0.5D).tex((double)f11, (double)f14).normal(-1.0F, 0.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f15, (double)f17, -0.5D).tex((double)f11, (double)f13).normal(-1.0F, 0.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f17, -0.5D).tex((double)f12, (double)f13).normal(1.0F, 0.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f18, -0.5D).tex((double)f12, (double)f14).normal(1.0F, 0.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f18, 0.5D).tex((double)f11, (double)f14).normal(1.0F, 0.0F, 0.0F).endVertex();
//	            bufferbuilder.pos((double)f16, (double)f17, 0.5D).tex((double)f11, (double)f13).normal(1.0F, 0.0F, 0.0F).endVertex();
//	            tessellator.draw();
//	         }
//	      }
//
//	   }
//	
//	private void setLightmap(PaintingEntity painting, float p_77008_2_, float p_77008_3_) {
//	      int i = MathHelper.floor(painting.posX);
//	      int j = MathHelper.floor(painting.posY + (double)(p_77008_3_ / 16.0F));
//	      int k = MathHelper.floor(painting.posZ);
//	      Direction direction = painting.getHorizontalFacing();
//	      if (direction == Direction.NORTH) {
//	         i = MathHelper.floor(painting.posX + (double)(p_77008_2_ / 16.0F));
//	      }
//
//	      if (direction == Direction.WEST) {
//	         k = MathHelper.floor(painting.posZ - (double)(p_77008_2_ / 16.0F));
//	      }
//
//	      if (direction == Direction.SOUTH) {
//	         i = MathHelper.floor(painting.posX - (double)(p_77008_2_ / 16.0F));
//	      }
//
//	      if (direction == Direction.EAST) {
//	         k = MathHelper.floor(painting.posZ + (double)(p_77008_2_ / 16.0F));
//	      }
//
//	      int l = this.renderManager.world.getCombinedLight(new BlockPos(i, j, k), 0);
//	      int i1 = l % 65536;
//	      int j1 = l / 65536;
//	      GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)i1, (float)j1);
//	      GlStateManager.color3f(1.0F, 1.0F, 1.0F);
//	   }
}