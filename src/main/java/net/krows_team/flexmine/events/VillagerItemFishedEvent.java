package net.krows_team.flexmine.events;

import java.util.List;

import javax.annotation.Nonnegative;

import com.google.common.base.Preconditions;

import net.krows_team.flexmine.entities.VillagerFishingBobberEntity;
import net.krows_team.flexmine.utils.DataRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

@Cancelable
public class VillagerItemFishedEvent extends LivingEvent {

	private NonNullList<ItemStack> items = NonNullList.create();
	
	private VillagerFishingBobberEntity hook;
	
	private int rodDamage;
	
	public VillagerItemFishedEvent(List<ItemStack> items, int rodDamage, VillagerFishingBobberEntity hook) {
		
		super((LivingEntity) DataRegistry.FISHING_MAP.get(hook));
		
		this.items.addAll(items);
		
		this.rodDamage = rodDamage;
		
		this.hook = hook;
	}
	
	public int getRodDamage() {
		
		return rodDamage;
	}
	
	public void damageRodBy(@Nonnegative int rodDamage) {
		
		Preconditions.checkArgument(rodDamage >= 0);
		
		this.rodDamage = rodDamage;
	}
	
	public NonNullList<ItemStack> getDrops() {
		
		return items;
	}
	
	public VillagerFishingBobberEntity getHookEntity() {
		
		return hook;
	}
}