package net.krows_team.flexmine;

import java.util.Random;

import net.krows_team.flexmine.blocks.CustomBlocks;
import net.krows_team.flexmine.containers.CustomContainers;
import net.krows_team.flexmine.effects.CustomEffects;
import net.krows_team.flexmine.entities.CustomEntities;
import net.krows_team.flexmine.items.CustomItems;
import net.krows_team.flexmine.items.recipes.CustomRecipes;
import net.krows_team.flexmine.registry.EntryContainer;
import net.krows_team.flexmine.render.RenderRegistries;
import net.krows_team.flexmine.screens.SpinnerScreen;
import net.krows_team.flexmine.tile_entities.CustomTileEntities;
import net.krows_team.flexmine.villagers.CustomTrades;
import net.krows_team.flexmine.villagers.poi.CustomPointOfInterestTypes;
import net.krows_team.flexmine.villagers.professions.CustomProfessions;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("flexmine")
public class FlexMine {
	
	public final static String MOD_ID = "flexmine";
	
	public FlexMine() {
		
		init();
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueue);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::process);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void init() {
		
		EntryContainer.applyEntries(CustomItems.INSTANCE);
		EntryContainer.applyEntries(CustomBlocks.INSTANCE);
		EntryContainer.applyEntries(CustomEffects.INSTANCE);
		EntryContainer.applyEntries(CustomEntities.INSTANCE);
		EntryContainer.applyEntries(CustomRecipes.INSTANCE);
		EntryContainer.applyEntries(CustomProfessions.INSTANCE);
		EntryContainer.applyEntries(CustomPointOfInterestTypes.INSTANCE);
		EntryContainer.applyEntries(CustomContainers.INSTANCE);
		EntryContainer.applyEntries(CustomTileEntities.INSTANCE);
		
		Registry.register(Registry.MENU, CustomContainers.SPINNER.getRegistryName(), CustomContainers.SPINNER);
	}
	
	private void setup(FMLCommonSetupEvent e) {
		
//		TODO Add Milk Consuming Behavior
		DispenserBlock.registerDispenseBehavior(Items.BUCKET, new OptionalDispenseBehavior() {
			
			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				
				World world = source.getWorld();
				
				if(!world.isRemote()) {
					
					successful = false;
					
					BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
					
					for(Entity entity : world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(pos), e -> e instanceof CowEntity)) {
						
						Random r = new Random();
						
						ItemEntity e = entity.entityDropItem(new ItemStack(Items.MILK_BUCKET), 1.0F);
						e.setMotion(e.getMotion().add((r.nextFloat() - r.nextFloat()) * 0.1F, 
													  (r.nextFloat() * 0.05F), 
													  (r.nextFloat() - r.nextFloat()) * 0.1F));
						
						stack.shrink(1);
						
						successful = true;
						
						return stack;
					}
				}
				
				return stack;
			}
		});
		
		CustomTrades.registry();
	}
	
	private void enqueue(InterModEnqueueEvent e) {
		
	}
	
	private void process(InterModProcessEvent e) {
		
	}
	
	private void clientSetup(FMLClientSetupEvent e) {
		
		RenderRegistries.register();
		
		ScreenManager.registerFactory(CustomContainers.SPINNER, SpinnerScreen::new);
	}
	
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent e) {
		
	}
}