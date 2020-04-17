package net.krows_team.flexmine.villagers;

import java.io.File;
import java.util.Random;

import net.krows_team.flexmine.utils.FileUtils;
import net.krows_team.flexmine.utils.TradeUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;

// TODO
public class CustomTrades {
	
	private static boolean registered;
	
	public static void registry() {
		
		if(!registered) {
			
			registered = true;
			
			System.out.println("Our Profession Folder: " + FileUtils.PROFESSIONS_FOLDER.getAbsolutePath());
			System.out.println(FileUtils.PROFESSIONS_FOLDER.exists());
			
			for(File file : FileUtils.PROFESSIONS_FOLDER.listFiles()) FileUtils.consumeOrCreate(file, s -> TradeUtils.loadTrades(file.getName().substring(0, file.getName().lastIndexOf('.')), s));
			
//			VillagerTrades.field_221239_a.put(CustomProfessions.BROKER, new Int2ObjectOpenHashMap<>(ImmutableMap.of(1, new VillagerTrades.ITrade[] {
//				
//				createBuilder(100, 2, 0).sellForEmerald(new ItemStack(Items.OBSIDIAN, 2)).build()
////				createTrade(Items.OBSIDIAN, 2, 100, 2),
////				createTrade(Items.GOLD_INGOT, 4, 15, 2)
//			})));
		}
	}
	
	protected static Builder createBuilder() {
		
		return new Builder();
	}
	
	protected static Builder createBuilder(int maxOffers, int xp, float priceMultiplier) {
		
		return new Builder(maxOffers, xp, priceMultiplier);
	}
	
	public static class Builder {
		
		private ItemStack toSell;
		private ItemStack toSellAdditional;
		private ItemStack toBuy;
		
		private int maxOffers;
		private int xp;
		
		private float priceMultiplier;
		
		public Builder() {
			
			this(4, 0, 0);
		}
		
		public Builder(int maxOffers, int xp, float priceMultiplier) {
			
			this.toSell = new ItemStack(Items.OBSIDIAN);
			
			this.toSellAdditional = ItemStack.EMPTY;
			
			this.toBuy = new ItemStack(Items.EMERALD);
			
			this.maxOffers = maxOffers;
			
			this.xp = xp;
			
			this.priceMultiplier = priceMultiplier;
		}
		
		public Builder buyForEmerald(Item toBuy) {
			
			return buyForEmeralds(toBuy, 1);
		}
		
		public Builder buyForEmerald(ItemStack toBuy) {
			
			return buyForEmeralds(toBuy, 1);
		}
		
		public Builder buyForEmeralds(Item toBuy, int emeraldAmount) {
			
			return buyForEmeralds(new ItemStack(toBuy), emeraldAmount);
		}
		
		public Builder buyForEmeralds(ItemStack toBuy, int emeraldAmount) {
			
			return barter(new ItemStack(Items.EMERALD, emeraldAmount), toBuy);
		}
		
		public Builder sellForEmerald(Item toSell) {
			
			return sellForEmeralds(toSell, 1);
		}
		
		public Builder sellForEmerald(ItemStack toSell) {
			
			return sellForEmeralds(toSell, 1);
		}
		
		public Builder sellForEmeralds(Item toSell, int emeraldAmount) {
			
			return sellForEmeralds(new ItemStack(toSell), emeraldAmount);
		}
		
		public Builder sellForEmeralds(ItemStack toSell, int emeraldAmount) {
			
			return barter(toSell, new ItemStack(Items.EMERALD, emeraldAmount));
		}
		
		public Builder sellForEmeralds(ItemStack toSell, ItemStack toSellAdditional, int emeraldAmount) {
			
			return barter(toSell, toSellAdditional, new ItemStack(Items.EMERALD, emeraldAmount));
		}
		
		public Builder barter(Item toSell, Item toBuy) {
			
			return barter(new ItemStack(toSell), new ItemStack(toBuy));
		}
		
		public Builder barter(ItemStack toSell, ItemStack toBuy) {
			
			return barter(toSell, ItemStack.EMPTY, toBuy);
		}
		
		public Builder barter(ItemStack toSell, ItemStack toSellAdditional, ItemStack toBuy) {
			
			this.toSell = toSell;
			
			this.toSellAdditional = toSellAdditional;
			
			this.toBuy = toBuy;
			
			System.out.println("Barter: " + toSell.getItem() + " + " + toSellAdditional.getItem() + " = " + toBuy.getItem());
			
			return this;
		}
		
		public ITrade build() {
			
			return new ITrade() {
				
				@Override
				public MerchantOffer getOffer(Entity p0, Random p1) {
					
					return new MerchantOffer(toSell, toSellAdditional, toBuy, maxOffers, xp, priceMultiplier);
				}
			};
		}
	}
}