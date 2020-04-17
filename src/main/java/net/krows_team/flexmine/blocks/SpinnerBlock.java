package net.krows_team.flexmine.blocks;

import net.krows_team.flexmine.FlexMine;
import net.krows_team.flexmine.tile_entities.SpinnerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SpinnerBlock extends ContainerBlock {
	
	public final static TranslationTextComponent NAME = new TranslationTextComponent(String.format("container.%s.spinner", FlexMine.MOD_ID));
	
	public SpinnerBlock(Properties properties) {
		
		super(properties);
		
		setDefaultState(stateContainer.getBaseState().with(BlockStateProperties.FACING, Direction.NORTH));
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
		
		if(!worldIn.isRemote) player.openContainer(state.getContainer(worldIn, pos));
		
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		
		if(stack.hasDisplayName()) {
			
			TileEntity entity = worldIn.getTileEntity(pos);
			
			if(entity instanceof SpinnerTileEntity) ((SpinnerTileEntity) entity).setCustomName(stack.getDisplayName());
		}
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		
		if(state.getBlock() != newState.getBlock()) {
			
			TileEntity entity = worldIn.getTileEntity(pos);
			
			if(entity instanceof SpinnerTileEntity) {
				
				InventoryHelper.dropInventoryItems(worldIn, pos, (SpinnerTileEntity) entity);
				
				worldIn.updateComparatorOutputLevel(pos, this);
			}
			
			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		
		return BlockRenderType.MODEL;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		
		return getDefaultState().with(BlockStateProperties.FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		
		builder.add(BlockStateProperties.FACING);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		
		return new SpinnerTileEntity();
	}
}