package net.krows_team.flexmine.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.InstantEffect;

public class SmallInstantHealthEffect extends InstantEffect {
	
	public SmallInstantHealthEffect(EffectType type, int color) {
		
		super(type, color);
	}
	
	@Override
	public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
		
		entityLivingBaseIn.heal(amplifier);
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
		
		entityLivingBaseIn.heal(amplifier);
	}
}