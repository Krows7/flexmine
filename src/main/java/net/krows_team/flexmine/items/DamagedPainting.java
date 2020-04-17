package net.krows_team.flexmine.items;

import net.krows_team.flexmine.entities.CustomEntities;
import net.krows_team.flexmine.entities.DamagedPaintingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HangingEntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DamagedPainting extends HangingEntityItem {
	
	public DamagedPainting(Properties properties) {
		
		super(CustomEntities.DAMAGED_PAINTING, properties);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		
		Direction direction = context.getFace();
		
		BlockPos pos = context.getPos();
		BlockPos pos1 = pos.offset(direction);
		
		PlayerEntity player = context.getPlayer();
		
		ItemStack item = context.getItem();
		
		if(player != null && !canPlace(player, direction, item, pos1)) return ActionResultType.FAIL;
		else {
			
			World world = context.getWorld();
			
//			TODO
			HangingEntity entity = new DamagedPaintingEntity(world, pos1, direction, PaintingType.AZTEC);
			
			CompoundNBT nbt = item.getTag();
			
			if(nbt != null) EntityType.applyItemNBT(world, player, entity, nbt);
			if(entity.onValidSurface()) {
				
				if(!world.isRemote) {
					
					entity.playPlaceSound();
					
					world.addEntity(entity);
				}
				
				item.shrink(1);
			}
			
			return ActionResultType.SUCCESS;
		}
	}
}