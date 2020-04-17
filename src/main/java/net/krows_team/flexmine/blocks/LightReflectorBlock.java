package net.krows_team.flexmine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightReflectorBlock extends Block {
	
	public LightReflectorBlock(Properties properties) {
		
		super(properties);
		
		setDefaultState(stateContainer.getBaseState().with(BlockStateProperties.FACING, Direction.NORTH).with(BlockStateProperties.POWERED, false));
	}
	
	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		
		worldIn.setBlockState(pos, state.with(BlockStateProperties.POWERED, worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up())));
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
		
		builder.add(BlockStateProperties.FACING, BlockStateProperties.POWERED);
	}
}