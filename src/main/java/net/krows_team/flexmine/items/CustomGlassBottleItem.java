package net.krows_team.flexmine.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class CustomGlassBottleItem extends GlassBottleItem {

	public CustomGlassBottleItem(Properties builder) {
		
		super(builder);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
		
		if(target instanceof CowEntity) {
			
			if(!playerIn.world.isRemote) playerIn.world.playSound(playerIn, playerIn.getPosition(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			
			turnBottleIntoItem(stack, playerIn, new ItemStack(CustomItems.MILK_BOTTLE));
			
			return true;
		}
		
		return false;
	}
}