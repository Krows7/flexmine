package net.krows_team.flexmine.blocks;

import java.util.Random;

import net.krows_team.flexmine.tile_entities.CustomJukeboxTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CustomJukeboxBlock extends JukeboxBlock {

	protected CustomJukeboxBlock(Properties properties) {
		
		super(properties);
	}
	
	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		
		CustomJukeboxTileEntity entity = getTileEntity(worldIn, pos);
		
		boolean powered = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
		boolean triggered = entity.isTriggered();
		
		if(powered && !triggered) {
			
			worldIn.getPendingBlockTicks().scheduleTick(pos, this, tickRate(worldIn));
			
			entity.setTriggered(true);
			
//			worldIn.setBlockState(pos, state.with(BlockStateProperties.TRIGGERED, true), 4);
			
//			TODO Wtf 4? What does it mean??!
		} else if(!powered) {
			
			entity.setTriggered(false);
			
//			worldIn.setBlockState(pos, state.with(BlockStateProperties.TRIGGERED, false), 4);
		}
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		
		return ((CustomJukeboxTileEntity) worldIn.getTileEntity(pos)).isTriggered() && !state.get(BlockStateProperties.HAS_RECORD) ? ActionResultType.FAIL : super.onBlockActivated(state, worldIn, pos, player, handIn, result);
	}
	
//	@Override
//	public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult result) {
//		
//		return ((CustomJukeboxTileEntity) world.getTileEntity(pos)).isTriggered() && !state.get(BlockStateProperties.HAS_RECORD) ? ActionResultType.FAIL : super.func_225533_a_(state, world, pos, entity, hand, result);
//	}
	
	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		
//		TODO Triggering to reject music
		worldIn.playEvent(1010, pos, 0);
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader p_196283_1_) {
		
		return new CustomJukeboxTileEntity();
	}
	
	private CustomJukeboxTileEntity getTileEntity(World world, BlockPos pos) {
		
		return (CustomJukeboxTileEntity) world.getTileEntity(pos);
	}
}