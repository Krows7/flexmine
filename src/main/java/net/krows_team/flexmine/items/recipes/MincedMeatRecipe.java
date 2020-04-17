package net.krows_team.flexmine.items.recipes;

import net.krows_team.flexmine.items.CustomItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;

public class MincedMeatRecipe extends ToolUseRecipe {

	public MincedMeatRecipe(ResourceLocation p_i48169_1_) {
		
		super(p_i48169_1_, () -> CustomRecipes.CRAFTING_MINCED_MEAT, SwordItem.class, Items.BEEF, new ItemStack(CustomItems.MINCED_MEAT));
	}
}