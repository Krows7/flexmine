package net.krows_team.flexmine.render;

import net.krows_team.flexmine.entities.CustomEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistries {
	
	public static void register() {
		
		RenderingRegistry.registerEntityRenderingHandler(CustomEntities.BULLET, BulletRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(CustomEntities.DAMAGED_PAINTING, DamagedPaintingRenderer::new);
	}
}