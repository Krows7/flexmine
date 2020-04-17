package net.krows_team.flexmine.render;

import net.krows_team.flexmine.FlexMine;
import net.krows_team.flexmine.entities.BulletEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BulletRenderer extends EntityRenderer<BulletEntity> {
	
	public final static ResourceLocation LOCATION = new ResourceLocation(FlexMine.MOD_ID, "textures/entity/projectiles/bullet.png");
	
	public BulletRenderer(EntityRendererManager renderManagerIn) {
		
		super(renderManagerIn);
	}
	
	@Override
	public ResourceLocation getEntityTexture(BulletEntity entity) {
		
		return null;
	}
}