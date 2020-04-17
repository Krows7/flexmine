package net.krows_team.flexmine.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MilkBottle extends Item {
	
	public MilkBottle(Properties properties) {
		
		super(properties);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		
		PlayerEntity player = entityLiving instanceof PlayerEntity ? (PlayerEntity) entityLiving : null;
		
		if(!worldIn.isRemote) entityLiving.clearActivePotions();
		if(entityLiving instanceof ServerPlayerEntity) {
			
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) entityLiving, stack);
			
			((ServerPlayerEntity) entityLiving).addStat(Stats.ITEM_USED.get(this));
		}
		if(player == null || !((PlayerEntity) entityLiving).abilities.isCreativeMode) {
			
			stack.shrink(1);
			
			if(stack.isEmpty()) return new ItemStack(Items.GLASS_BOTTLE);
		}
		if(player != null) {
			
			if(!player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
				
				player.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
			}
		}
		
		return stack;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		
		return 32;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		
		return UseAction.DRINK;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		playerIn.setActiveHand(handIn);
		
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
	}
}