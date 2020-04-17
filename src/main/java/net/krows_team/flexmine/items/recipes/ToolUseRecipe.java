package net.krows_team.flexmine.items.recipes;

import java.util.function.Supplier;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ToolUseRecipe extends SpecialRecipe {
	
	private Class<? extends TieredItem> clazz;
	
	private Item item;
	
	private ItemStack result;
	
	private Supplier<IRecipeSerializer<?>> irs;
	
	public ToolUseRecipe(ResourceLocation p_i48169_1_, Supplier<IRecipeSerializer<?>> irs, Class<? extends TieredItem> clazz, Item item, ItemStack result) {
		
		super(p_i48169_1_);
		
		this.clazz = clazz;
		
		this.item = item;
		
		this.result = result;
		
		this.irs = irs;
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		
		ItemStack tool = null;
		ItemStack resultItem = null;
		
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			
			ItemStack item = inv.getStackInSlot(i);
			
			if(!item.isEmpty()) {
				
				if(clazz.isInstance(item.getItem())) {
					
					if(tool != null) return false;
					else tool = item;
				} else if(item.getItem() == this.item) {
					
					if(resultItem != null) return false;
					else resultItem = item;
				} else return false;
			}
		}
		
		return tool != null && resultItem != null;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {

		ItemStack tool = null;
		ItemStack resultItem = null;
		
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			
			ItemStack item = inv.getStackInSlot(i);
			
			if(!item.isEmpty()) {
				
				if(clazz.isInstance(item.getItem())) {
					
					if(tool != null) return ItemStack.EMPTY;
					else tool = item;
				} else if(item.getItem() == this.item) {
					
					if(resultItem != null) return ItemStack.EMPTY;
					else resultItem = item;
				} else return ItemStack.EMPTY;
			}
		}
		
		if(tool == null || resultItem == null) return ItemStack.EMPTY;
		
		return result.copy();
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
		
		NonNullList<ItemStack> list = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		
		for(int i = 0; i < list.size(); ++i) {
			
			ItemStack item = inv.getStackInSlot(i);
			
			if(item.hasContainerItem()) list.set(i, item.getContainerItem());
			else if(clazz.isInstance(item.getItem())) {
				
				if(item.getDamage() != item.getMaxDamage() - 1) {
					
					item = item.copy();
					item.setDamage(item.getDamage() + 1);
					item.setCount(1);
					
					list.set(i, item);
					
					break;
				}
			}
		}
		
		return list;
	}
	
	@Override
	public boolean canFit(int width, int height) {
		
		return width * height >= 2;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		
		return irs.get();
	}
}