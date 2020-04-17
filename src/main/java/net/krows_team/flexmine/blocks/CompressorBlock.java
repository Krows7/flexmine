package net.krows_team.flexmine.blocks;

import net.krows_team.flexmine.tile_entities.CompressorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CompressorBlock extends Block {
	
	
	
	public CompressorBlock(Properties properties) {
		
		super(properties);
		
		setDefaultState(stateContainer.getBaseState().with(BlockStateProperties.POWERED, false));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		
		builder.add(BlockStateProperties.POWERED);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		
		if(stack.hasDisplayName()) {
			
//			TODO
//			TileEntity entity = worldIn.getTileEntity(pos);
//			
//			if(entity instanceof CompressorTileEntity) ()
		}
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		
		if(state.getBlock() == newState.getBlock()) return;
		
		TileEntity entity = worldIn.getTileEntity(pos);
		
		if(entity instanceof CompressorTileEntity) InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) entity);
		
		super.onReplaced(state, worldIn, pos, newState, isMoving);
	}
}