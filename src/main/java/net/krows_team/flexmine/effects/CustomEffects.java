package net.krows_team.flexmine.effects;

import net.krows_team.flexmine.registry.FlexMineEntryContainer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class CustomEffects extends FlexMineEntryContainer<Effect> {
	
	public final static CustomEffects INSTANCE = new CustomEffects();
	
	public final static Effect SMALL_INSTANT_HEALTH = INSTANCE.load(new SmallInstantHealthEffect(EffectType.BENEFICIAL, 16262179), "small_instant_health");
	public final static Effect PURIFICATION = INSTANCE.load(new PurificationEffect(EffectType.NEUTRAL, 16777215), "purification");
}