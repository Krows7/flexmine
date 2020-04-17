package net.krows_team.flexmine.items.recipes;

import net.krows_team.flexmine.items.CustomItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PumpkinSliceRecipe extends SpecialRecipe {

	public PumpkinSliceRecipe(ResourceLocation idIn) {
		
		super(idIn);
	}

	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		
		ItemStack axe = null;
		ItemStack pumpkin = null;
		
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			
			ItemStack item = inv.getStackInSlot(i);
			
			if(!item.isEmpty()) {
				
				if(item.getItem() instanceof AxeItem) {
					
					if(axe != null) return false;
					else axe = item;
				} else if(item.getItem() == Items.PUMPKIN) {
					
					if(pumpkin != null) return false;
					else pumpkin = item;
				} else return false;
			}
		}
		
		return axe != null && pumpkin != null;
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
		
		ItemStack axe = null;
		ItemStack pumpkin = null;
		
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			
			ItemStack item = inv.getStackInSlot(i);
			
			if(!item.isEmpty()) {
				
				if(item.getItem() instanceof AxeItem) {
					
					if(axe != null) return ItemStack.EMPTY;
					else axe = item;
				} else if(item.getItem() == Items.PUMPKIN) {
					
					if(pumpkin != null) return ItemStack.EMPTY;
					else pumpkin = item;
				} else return ItemStack.EMPTY;
			}
		}
		
		if(axe == null || pumpkin == null) return ItemStack.EMPTY;
		
		return new ItemStack(CustomItems.PUMPKIN_SLICE, 4);
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
		
		NonNullList<ItemStack> list = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		
		for(int i = 0; i < list.size(); ++i) {
			
			ItemStack item = inv.getStackInSlot(i);
			
			if(item.hasContainerItem()) list.set(i, item.getContainerItem());
			else if(item.getItem() instanceof AxeItem) {
				
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
		
		return CustomRecipes.CRAFTING_PUMPKIN_SLICE;
	}
}