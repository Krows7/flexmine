package net.krows_team.flexmine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class PizzaBlock extends Block {
	
	public final static IntegerProperty BITES = IntegerProperty.create("bites", 0, 3);
	private final static VoxelShape[] SHAPES = new VoxelShape[4];
	
	static {
		
		SHAPES[0] = Block.makeCuboidShape(1, 0, 1, 15, 6, 15);
		SHAPES[3] = Block.makeCuboidShape(8, 0, 1, 15, 6, 8);
		SHAPES[2] = Block.makeCuboidShape(1, 0, 1, 15, 6, 8);
		SHAPES[1] = VoxelShapes.combine(SHAPES[2], Block.makeCuboidShape(1, 0, 8, 8, 6, 15), IBooleanFunction.OR);
	}
	
	public PizzaBlock(Properties properties) {
		
		super(properties);
		
		setDefaultState(stateContainer.getBaseState().with(BITES, 0));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		
		return SHAPES[state.get(BITES)];
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
		
		if(worldIn.isRemote()) {
			
			ItemStack item = player.getHeldItem(handIn);
			
			if(tryEat(worldIn, pos, state, player) == ActionResultType.SUCCESS) return ActionResultType.SUCCESS;
			if(item.isEmpty()) return ActionResultType.CONSUME;
		}
		
		return tryEat(worldIn, pos, state, player);
	}
	
	private ActionResultType tryEat(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		
		if(!player.canEat(false)) return ActionResultType.PASS;
		
		player.getFoodStats().addStats(4, 1.5F);
		
		int bites = state.get(BITES);
		
		if(bites < 3) world.setBlockState(pos, state.with(BITES, bites + 1), 3);
		else world.removeBlock(pos, false);
		
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		
		if(facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos)) return Blocks.AIR.getDefaultState();
		
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		
		builder.add(BITES);
	}
	
	@Override
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		
		return 4 - blockState.get(BITES) * 2;
	}
	
	@Override
	public boolean hasComparatorInputOverride(BlockState state) {
		
		return true;
	}
	
	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		
		return false;
	}
}