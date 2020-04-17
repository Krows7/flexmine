package net.krows_team.flexmine.items;

import net.krows_team.flexmine.entities.VillagerFishingBobberEntity;
import net.krows_team.flexmine.utils.DataRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class CustomFishingRodItem extends FishingRodItem {
	
	public CustomFishingRodItem(Properties p_i48494_1_) {
		
		super(p_i48494_1_);
	}

	public void useItem(World world, LivingEntity entity) {
		
		ItemStack item = entity.getHeldItemMainhand();
		
		if(!world.isRemote()) {
			
			int a = EnchantmentHelper.getFishingSpeedBonus(item);
			int b = EnchantmentHelper.getFishingLuckBonus(item);
			
			world.addEntity(new VillagerFishingBobberEntity(entity, world, a, b));
		}
	}
	
	public void unUseItem(World world, LivingEntity entity) {
		
		ItemStack item = entity.getHeldItemMainhand();
		
		world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		
		if(!world.isRemote()) {
			
			FishingBobberEntity bobber = (FishingBobberEntity) DataRegistry.FISHING_MAP.remove(entity);
			
			item.damageItem(bobber.handleHookRetraction(item), entity, e -> e.sendBreakAnimation(Hand.MAIN_HAND));
			
			world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		}
	}
}