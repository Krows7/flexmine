package net.krows_team.flexmine.entities;

import javax.annotation.Nullable;

import net.krows_team.flexmine.items.CustomItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DamagedPaintingEntity extends PaintingEntity {
	
	public DamagedPaintingEntity(EntityType<? extends PaintingEntity> type, World world) {
		
		super(type, world);
	}
	
	@OnlyIn(Dist.CLIENT)
	public DamagedPaintingEntity(World world, BlockPos pos, Direction direction, PaintingType type) {
		
		super(world, pos, direction, type);
	}
	
	@Override
	public void onBroken(@Nullable Entity brokenEntity) {
		
		if(world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
			
			playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0F, 1.0F);
			
			if(brokenEntity instanceof PlayerEntity && ((PlayerEntity) brokenEntity).abilities.isCreativeMode) return;
			
			entityDropItem(CustomItems.DAMAGED_PAINTING);
		}
	}
}