package net.krows_team.flexmine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpikesBlock extends Block {
	
	public SpikesBlock(Properties properties) {
		
		super(properties);
	}
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		
		entityIn.attackEntityFrom(DamageSource.CACTUS, 2.0F);
	}
}