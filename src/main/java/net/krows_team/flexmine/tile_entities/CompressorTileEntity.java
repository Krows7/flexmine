package net.krows_team.flexmine.tile_entities;

import net.krows_team.flexmine.containers.CompressorContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CompressorTileEntity extends LockableTileEntity {
	
	protected NonNullList<ItemStack> items;
	
	private int cookTime;
	
	private IIntArray data = new IIntArray() {
		
		@Override
		public int size() {
			
			return 2;
		}
		
		@Override
		public void set(int p0, int p1) {
			
			
		}
		
		@Override
		public int get(int p0) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	@Override
	public void read(CompoundNBT compound) {
		
//		TODO
		super.read(compound);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		
//		TODO
		return super.write(compound);
	}
	
	protected CompressorTileEntity(TileEntityType<?> typeIn) {
		
		super(typeIn);
		
		items = NonNullList.withSize(2, ItemStack.EMPTY);
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack item) {
		
		return index == 0;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		
		return ItemStackHelper.getAndSplit(items, index, count);
	}

	@Override
	public int getSizeInventory() {
		
		return items.size();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		
		return items.get(index);
	}

	@Override
	public boolean isEmpty() {
		
		for(ItemStack item : items) if(!item.isEmpty()) return false;
		
		return true;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		
		return world.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		
		return ItemStackHelper.getAndRemove(items, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
		ItemStack item = items.get(index);
		
		boolean flag = !stack.isEmpty() && stack.isItemEqual(item) && ItemStack.areItemStackTagsEqual(stack, item);
		
		items.set(index, stack);
		
		if(stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());
		
		if(index == 0 && !flag) {
			
			cookTime = 0;
			
			markDirty();
		}
	}

	@Override
	public void clear() {
		
		items.clear();
	}

	@Override
	protected ITextComponent getDefaultName() {
		
		return new TranslationTextComponent("container.flexmine.compressor");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		
		return new CompressorContainer(id, player, this, data);
	}
}