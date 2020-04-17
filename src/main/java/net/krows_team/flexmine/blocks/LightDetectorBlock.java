package net.krows_team.flexmine.blocks;

import java.util.Random;

import net.krows_team.flexmine.tile_entities.LightDetectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LightDetectorBlock extends Block {

	public LightDetectorBlock(Properties properties) {
		
		super(properties);
		
		setDefaultState(stateContainer.getBaseState().with(BlockStateProperties.FACING, Direction.NORTH).with(BlockStateProperties.TRIGGERED, false));
	}
	
	@Override
	public boolean canProvidePower(BlockState state) {
		
		return true;
	}
	
	@Override
	public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
		
		return blockState.get(BlockStateProperties.TRIGGERED) ? 15 : 0;
	}
	
	@Override
	public int tickRate(IWorldReader worldIn) {
		
		return 20;
	}
	
	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		
		if(!state.get(BlockStateProperties.TRIGGERED)) return;
		if(LightDetectorTileEntity.raycast(state, world, pos)) {
			
			onActivated(state, world, pos);
		} else {
			
			world.setBlockState(pos, state.with(BlockStateProperties.TRIGGERED, false), 3);
			
			updateNeighbors(state, world, pos, 3);
		}
	}
	
//	TODO
	public void onActivated(BlockState state, World world, BlockPos pos) {
		
		world.setBlockState(pos, state.with(BlockStateProperties.TRIGGERED, true), 3);
		
		updateNeighbors(state, world, pos, 3);
		
		world.getPendingBlockTicks().scheduleTick(pos, this, tickRate(world));
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		
		return new LightDetectorTileEntity();
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		
		if(isMoving || state.getBlock() == newState.getBlock()) return;
		if(state.get(BlockStateProperties.TRIGGERED)) {
			
			worldIn.notifyNeighborsOfStateChange(pos, this);
			worldIn.notifyNeighborsOfStateChange(pos.offset(state.get(BlockStateProperties.FACING).getOpposite()), this);
		}
		
		super.onReplaced(state, worldIn, pos, newState, isMoving);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		
		return getDefaultState().with(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		
		return state.with(BlockStateProperties.FACING, rot.rotate(state.get(BlockStateProperties.FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		
		return state.rotate(mirrorIn.toRotation(state.get(BlockStateProperties.FACING)));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		
		builder.add(BlockStateProperties.FACING, BlockStateProperties.TRIGGERED);
	}
}