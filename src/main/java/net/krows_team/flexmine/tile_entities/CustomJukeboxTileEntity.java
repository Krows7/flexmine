package net.krows_team.flexmine.tile_entities;

import net.krows_team.flexmine.blocks.CustomJukeboxBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.util.Direction;

public class CustomJukeboxTileEntity extends JukeboxTileEntity implements ISidedInventory {
	
	private boolean powered;
	private boolean triggered;
	
	private final static int[] SLOTS = {0};
	
	@Override
	public void read(CompoundNBT nbt) {
		
		super.read(nbt);
		
		if(nbt.getBoolean(BlockStateProperties.TRIGGERED.getName())) triggered = true;
		if(nbt.getBoolean(BlockStateProperties.POWERED.getName())) powered = true;
	}
	
	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		
		super.write(nbt);
		
		nbt.putBoolean(BlockStateProperties.TRIGGERED.getName(), triggered);
		nbt.putBoolean(BlockStateProperties.POWERED.getName(), powered);
		
		return nbt;
	}
	
	@Override
	public int getSizeInventory() {
		
		return 1;
	}

	@Override
	public boolean isEmpty() {
		
		return getRecord() == ItemStack.EMPTY;
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return index == 0 ? getRecord() : null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		
		BlockState state = world.getBlockState(pos).with(BlockStateProperties.HAS_RECORD, false);
		
		world.setBlockState(pos, state, 2);
		
		triggered = false;
		
		ItemStack item = getRecord();
		
		clear();
		
		return item;
	}
	
	@Override
	public int getInventoryStackLimit() {
		
		return 1;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		
		return decrStackSize(index, 0);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
		BlockState state = world.getBlockState(pos);
		
		triggered = false;
		
		((CustomJukeboxBlock) state.getBlock()).insertRecord(world, pos, state, stack);
		
		world.playEvent(null, 1010, pos, Item.getIdFromItem(stack.getItem()));
	}
	
	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack item) {
		
		return item.getItem() instanceof MusicDiscItem;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity p0) {
		
		return false;
	}

	@Override
	public int[] getSlotsForFace(Direction p0) {
		
		return SLOTS;
	}

	@Override
	public boolean canInsertItem(int p0, ItemStack p1, Direction p2) {
		
		BlockState state = world.getBlockState(pos);
		
		return !triggered && !state.get(JukeboxBlock.HAS_RECORD);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack item, Direction direction) {
		
		return !isEmpty() && triggered; 
	}
	
	public void setTriggered(boolean triggered) {
		
		boolean pow = isPowered();
		
		setPowered(triggered);
		
		if(triggered && (!world.getBlockState(pos).get(BlockStateProperties.HAS_RECORD) || pow)) return;
		
		this.triggered = triggered;
		
		markDirty();
	}
	
	public boolean isTriggered() {
		
		return triggered;
	}
	
	public void setPowered(boolean powered) {
		
		this.powered = powered;
	}
	
	public boolean isPowered() {
		
		return powered;
	}
}