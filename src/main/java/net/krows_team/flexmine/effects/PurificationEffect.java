package net.krows_team.flexmine.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.InstantEffect;

public class PurificationEffect extends InstantEffect {

	public PurificationEffect(EffectType type, int color) {
		
		super(type, color);
	}
	
	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
		
		entityLivingBaseIn.clearActivePotions();
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
		
		entityLivingBaseIn.clearActivePotions();
	}
}