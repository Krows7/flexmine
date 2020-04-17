package net.krows_team.flexmine.tasks;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import net.krows_team.flexmine.items.CustomFishingRodItem;
import net.krows_team.flexmine.items.CustomItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ForgeEventFactory;

public class FishingTask extends Task<VillagerEntity> {
	
	private final static Set<Item> FISHING_SET = ImmutableSet.of(Items.FISHING_ROD);
	
	private boolean hasFishingRod;
	
	private long executionTime;
	
	private int e;
	
	private List<BlockPos> blockCache = Lists.newArrayList();
	
	private BlockPos waterPos;
	
	public FishingTask() {
		
		super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleStatus.VALUE_PRESENT));
	}
	
	@Override
	protected boolean shouldExecute(ServerWorld world, VillagerEntity entity) {
		
		if(!ForgeEventFactory.getMobGriefingEvent(world, entity) || entity.getVillagerData().getProfession() != VillagerProfession.FISHERMAN) return false;
		
		Inventory inventory = entity.getVillagerInventory();
		
		hasFishingRod = inventory.hasAny(FISHING_SET);
		
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			
			ItemStack item = inventory.getStackInSlot(i);
			
			if(item.getItem() == Items.FISHING_ROD) {
				
				hasFishingRod = true;
				
				break;
			}
		}
		
		Mutable pos = new Mutable(entity);
		
		blockCache.clear();
		
		for(int i = - 1; i <= 1; ++i) {
			
			for(int j = - 1; j <= 1; ++j) {
				
				for(int k = - 1; k <= 1; ++k) {
					
					pos.setPos(entity.getPosX() + i, entity.getPosY() + j, entity.getPosZ() + k);
					
					if(isWater(pos, world)) blockCache.add(new BlockPos(pos));
				}
			}
		}
		
		waterPos = blockCache.isEmpty() ? null : blockCache.get(world.getRandom().nextInt(blockCache.size()));
		
		return hasFishingRod && waterPos != null;
	}
	
	private boolean isWater(BlockPos pos, World world) {
		
		BlockState state = world.getBlockState(pos);
		
		Block block = state.getBlock();
		
		return block == Blocks.WATER;
	}
	
	@Override
	protected void startExecuting(ServerWorld p_212831_1_, VillagerEntity entity, long gameTime) {
		
		if(gameTime > executionTime && waterPos != null) {
			
			entity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(waterPos));
			entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosWrapper(waterPos), 0.5F, 1));
		}
	}
	
	@Override
	protected void resetTask(ServerWorld world, VillagerEntity entity, long gameTime) {
		
		entity.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
		entity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
		
		e = 0;
		
		executionTime = gameTime + 40L;
	}
	
//	TODO
	@Override
	protected void updateTask(ServerWorld world, VillagerEntity entity, long gameTime) {
		
		if(waterPos != null && gameTime > executionTime) {
			
			BlockState state = world.getBlockState(waterPos);
			
			Block block = state.getBlock();
			
			Inventory inventory = entity.getVillagerInventory();
			
			for(int i = 0; i < inventory.getSizeInventory(); ++i) {
				
				ItemStack item = inventory.getStackInSlot(i);
				
				if(!item.isEmpty()) {
					
					if(item.getItem() == CustomItems.VANILLA_FISHING_ROD) {
						
						((CustomFishingRodItem) item.getItem()).useItem(world, entity);
						
						break;
					}
				}
				
				entity.rotationPitch = 0;
				
			}
			
			e++;
		}
	}
	
	@Override
	protected boolean shouldContinueExecuting(ServerWorld p_212834_1_, VillagerEntity p_212834_2_, long p_212834_3_) {
		
		return e < 200;
	}
}