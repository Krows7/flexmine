package net.krows_team.flexmine.tile_entities;

import java.util.Optional;

import net.krows_team.flexmine.blocks.CustomBlocks;
import net.krows_team.flexmine.blocks.LightDetectorBlock;
import net.krows_team.flexmine.utils.Reflections;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightDetectorTileEntity extends TileEntity implements ITickableTileEntity {
	
	private final static RayTraceContext INSTANCE = Reflections.create(RayTraceContext.class);
	
	private final static int MAX_RANGE = 20;
	
	static {
		
		Reflections.editField(INSTANCE, "blockMode", BlockMode.COLLIDER);
		Reflections.editField(INSTANCE, "fluidMode", FluidMode.NONE);
	}
	
	public LightDetectorTileEntity() {
		
//		TODO
		super(CustomTileEntities.LIGHT_DETECTOR);
	}

	@Override
	public void tick() {
		
		if(!getBlockState().get(BlockStateProperties.TRIGGERED) && raycast(getBlockState(), world, pos)) ((LightDetectorBlock) getBlockState().getBlock()).onActivated(getBlockState(), world, pos);
	}
	
	public static boolean raycast(BlockState state, World world, BlockPos pos) {
		
		Direction d = state.get(BlockStateProperties.FACING);
		
		Vec3d point = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
		Vec3d direction = new Vec3d(d.getDirectionVec());
		Vec3d end = null;
		
		boolean flag = false;
		
		for(int i = 1; i < MAX_RANGE + 2; i++) {
			
			end = point.add(direction.scale(i));
			
			BlockState endState = world.getBlockState(new BlockPos(end));
			Block block = endState.getBlock();
			
			if(block == Blocks.AIR) continue;
			if(block == CustomBlocks.LIGHT_REFLECTOR && endState.get(BlockStateProperties.POWERED) && endState.get(BlockStateProperties.FACING) == state.get(BlockStateProperties.FACING).getOpposite()) {
				
				flag = true;
				
				break;
			} else return false;
		}
		
		if(flag) {
			
			AxisAlignedBB aabb = new AxisAlignedBB(pos).expand(end.subtract(point));
			
			Vec3d offset = null;
			
			if(d.getAxis().isVertical()) offset = new Vec3d(1, d.getYOffset() * 2, 1);
			else offset = new Vec3d(d.rotateY().getDirectionVec());
			
			offset = offset.mul(offset).scale(0.5);
			
			point = point.add(offset);
			
			end = end.add(offset);
			
			for(Entity entity : world.getEntitiesInAABBexcluding(null, aabb, null)) {
				
				if(entity.getBoundingBox().rayTrace(point, end).isPresent()) return true;
			}
		}
		
		return false;
	}
	
	private void raycast() {
		
		if(getBlockState().get(BlockStateProperties.TRIGGERED)) return;
		
		System.out.println();
		System.out.println("Current Position: " + pos);
		
		Direction d = getBlockState().get(BlockStateProperties.FACING);
		
		Vec3d point = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
		Vec3d direction = new Vec3d(d.getDirectionVec());
		Vec3d end = null;
		
		boolean flag = false;
		
		for(int i = 1; i < 20; i++) {
			
			end = point.add(direction.scale(i));
			
			BlockState state = world.getBlockState(new BlockPos(end));
			Block block = state.getBlock();
			
			if(block == Blocks.AIR) continue;
			if(block == CustomBlocks.LIGHT_REFLECTOR && state.get(BlockStateProperties.FACING) == getBlockState().get(BlockStateProperties.FACING).getOpposite()) {
				
				System.out.println("Found Right Receiver!");
				
				flag = true;
				
				break;
			} else {
				
				if(block == CustomBlocks.LIGHT_REFLECTOR) System.out.println("Wrong Receiver Direction: " + state.get(BlockStateProperties.FACING) + ", current: " + getBlockState().get(BlockStateProperties.FACING));
				else System.out.println("Found Incorrect Block!: " + block + " at: " + end);
				
				world.setBlockState(pos, getBlockState().with(BlockStateProperties.TRIGGERED, false));
				
				return;
			}
		}
		
		if(flag) {
			
			System.out.println("Searching for Entities...");
			
			AxisAlignedBB aabb = new AxisAlignedBB(pos).expand(end.subtract(point));
			
			Vec3d offset = new Vec3d(d.rotateY().getDirectionVec()).scale(0.5);
			
			point = point.add(offset);
			
			end = end.add(offset);
			
			for(Entity entity : world.getEntitiesInAABBexcluding(null, aabb, null)) {
				
				Optional<Vec3d> rayTrace = entity.getBoundingBox().rayTrace(point, end);
				
				if(rayTrace.isPresent()) {
					
					System.out.println("Found Entity on Intersection!)");
					
					world.setBlockState(pos, getBlockState().with(BlockStateProperties.TRIGGERED, true));
					
					return;
				}
			}
			
			System.out.println("Entities weren't Found!(");
		}
		
		System.out.println("Didn't Found Any Block((((");
		
		world.setBlockState(pos, getBlockState().with(BlockStateProperties.TRIGGERED, false));
		
//		Vec3d direction = new Vec3d(getBlockState().get(BlockStateProperties.FACING).getDirectionVec());
//		Vec3d start = new Vec3d(pos.getX(), pos.getY(), pos.getZ()).add(direction.scale(0.1));
//		Vec3d end = start.add(direction.scale(20));
//		
//		RayTraceResult result = world.rayTraceBlocks(editInstance(start, end));
//		
//		System.out.println(world.getBlockState(((BlockRayTraceResult) result).getPos()).getBlock());
//		
//		if(result.getType() == Type.ENTITY) {
//			
//			world.setBlockState(pos, getBlockState().with(BlockStateProperties.TRIGGERED, true));
//		} else if(result.getType() == Type.BLOCK) {
//			
//			BlockPos bp = ((BlockRayTraceResult) result).getPos();
//			BlockState state = world.getBlockState(bp);
//			
//			world.setBlockState(bp, state.with(BlockStateProperties.TRIGGERED, state.getBlock() == CustomBlocks.LIGHT_RECEIVER));
//		} else world.setBlockState(pos, getBlockState().with(BlockStateProperties.TRIGGERED, false));
	}
	
	private RayTraceContext editInstance(Vec3d start, Vec3d end) {
		
		Reflections.editField(INSTANCE, "startVec", start);
		Reflections.editField(INSTANCE, "endVec", end);
		
		return INSTANCE;
	}
}