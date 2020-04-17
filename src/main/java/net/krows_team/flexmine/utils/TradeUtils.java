package net.krows_team.flexmine.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.krows_team.flexmine.FlexMine;
import net.krows_team.flexmine.villagers.CustomTrades;
import net.krows_team.flexmine.villagers.CustomTrades.Builder;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class TradeUtils {
	
	public static void loadTrades(String name, String json) {
		
		VillagerProfession profession = ForgeRegistries.PROFESSIONS.getValue(new ResourceLocation(FlexMine.MOD_ID, name));
		
		JsonObject object = (JsonObject) new JsonParser().parse(json);
		
		VillagerTrades.VILLAGER_DEFAULT_TRADES.put(profession, new Int2ObjectArrayMap<>(ImmutableMap.of(1, getTrades(object, 1),
																							   2, getTrades(object, 2), 
																							   3, getTrades(object, 3),
																							   4, getTrades(object, 4),
																							   5, getTrades(object, 5))));
	}
	
	public static ITrade getTrade(String type, JsonObject json) {
		
		JSONProvider provider = new JSONProvider(json);
		
		CustomTrades.Builder builder = new Builder(provider.get("max_offers", e -> e.getAsInt(), 4), provider.get("xp", e -> e.getAsInt(), 0), provider.get("price_multiplier", e -> e.getAsFloat(), 0.0F));
		
		switch(type) {
		
		case "sell" : 
			
			builder.sellForEmeralds(getItem(json, "wanted"), getItem(json, "wanted_2"), provider.get("emeralds", e -> e.getAsInt(), 1));
			
			break;
			
		case "buy" :
			
			builder.buyForEmeralds(getItem(json, "given"), provider.get("emeralds", e -> e.getAsInt(), 1));
			
			break;
		case "barter" :
			
			builder.barter(getItem(json, "wanted"), getItem(json, "wanted_2"), getItem(json, "given"));
			
			break;
		default : return null;
		}
		
		return builder.build();
	}
	
	public static ItemStack getItem(JsonObject object, String key) {
		
		if(object.get(key) == null) return ItemStack.EMPTY;
		
		return getItem(new JSONProvider(object.getAsJsonObject(key)));
	}
	
	public static ItemStack getItem(JSONProvider provider) {
		
		try {
			
			ItemStack item = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(provider.get("item", e -> e.getAsString()))), provider.get("count", e -> e.getAsInt(), 1));
			item.setTag(provider.get("nbt", TradeUtils::getNBT));
			
			return item;
		} catch(Exception e) {
			
			return ItemStack.EMPTY;
		}
	}
	
	public static ITrade[] getTrades(JsonObject json, int level) {
		
		JsonElement element = json.get("level_" + level);
		
		if(element == null) return new ITrade[0];
		
		return getTrades((JsonArray) element);
	}
	
	public static ITrade[] getTrades(JsonArray array) {
		
		List<ITrade> tradeList = new ArrayList<VillagerTrades.ITrade>(array.size());
		
		for(JsonElement element : array) {
			
			String type = element.getAsJsonObject().get("type").getAsString();
			
			tradeList.add(getTrade(type, element.getAsJsonObject().getAsJsonObject("data")));
		}
		
		return tradeList.toArray(new ITrade[array.size()]);
	}
	
	public static Optional<JsonElement> get(JsonObject object, String key) {
		
		return Optional.ofNullable(object.get(key));
	}
	
	private static CompoundNBT getNBT(JsonElement element) {
		
		try {
			
			return JsonToNBT.getTagFromJson(element.toString());
		} catch (CommandSyntaxException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static class JSONProvider {
		
		private JsonObject json;
		
		public JSONProvider(JsonObject json) {
			
			this.json = json;
		}
		
		public <T> T get(String key, Function<JsonElement, T> function, T byDefault) {
			
			JsonElement element = json.get(key);
			
			return element == null ? byDefault : function.apply(element);
		}
		
		public <T> T get(String key, Function<JsonElement, T> function) {
			
			return get(key, function, null);
		}
	}
}