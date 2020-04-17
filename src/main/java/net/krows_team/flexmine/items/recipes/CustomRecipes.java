package net.krows_team.flexmine.items.recipes;

import net.krows_team.flexmine.registry.FlexMineEntryContainer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;

public class CustomRecipes extends FlexMineEntryContainer<IRecipeSerializer<?>> {
	
	public final static CustomRecipes INSTANCE = new CustomRecipes();
	
	public final static IRecipeSerializer<PumpkinSliceRecipe> CRAFTING_PUMPKIN_SLICE = create(new SpecialRecipeSerializer<>(PumpkinSliceRecipe::new), "crafting_pumpkin_slice");
	public final static IRecipeSerializer<MincedMeatRecipe> CRAFTING_MINCED_MEAT = create(new SpecialRecipeSerializer<IRecipe<?>>(MincedMeatRecipe::new), "crafting_minced_meat");
	
	@SuppressWarnings("unchecked")
	private static <T extends IRecipe<?>> IRecipeSerializer<T> create(IRecipeSerializer<?> serializer, String name) {
		
		return (IRecipeSerializer<T>) INSTANCE.load(serializer, name);
	}
}