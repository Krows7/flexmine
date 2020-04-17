package net.krows_team.flexmine.tile_entities;

import net.krows_team.flexmine.blocks.SpinnerBlock;
import net.krows_team.flexmine.containers.SpinnerContainer;
import net.krows_team.flexmine.items.CustomItems;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class SpinnerTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHelperPopulator, ITickableTileEntity {
	
	public final static int[] SLOTS_UP = new int[] {0};
	public final static int[] SLOTS_DOWN = new int[] {2};
	public final static int[] SLOTS_HORIZONTAL = new int[] {1};
	
	private int MAX_WORK_TIME = 100;
	protected int workTime;
	
	protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
	
	protected final IIntArray data = new IIntArray() {
		
		@Override
		public int size() {
			
			return 2;
		}
		
		@Override
		public void set(int index, int value) {
			
			switch(index) {
			
			case 0 : workTime = value; break;
			case 1 : MAX_WORK_TIME = value; break;
			}
		}
		
		@Override
		public int get(int index) {
			
			switch(index) {
			
			case 0 : return workTime;
			case 1 : return MAX_WORK_TIME;
			default : return 0;
			}
		}
	};
	
	public SpinnerTileEntity() {
		
		super(CustomTileEntities.SPINNER);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		
		super.read(compound);
		
		items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		
		ItemStackHelper.loadAllItems(compound, items);
		
		workTime = compound.getInt("WorkTime");
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		
		super.write(compound);
		
		compound.putInt("WorkTime", workTime);
		
		ItemStackHelper.saveAllItems(compound, items);
		
		return compound;
	}
	
//	TODO
	private boolean isWorking() {
		
		return workTime > 0;
	}

	@Override
	public int getSizeInventory() {
		
		return 3;
	}

	@Override
	public boolean isEmpty() {
		
		for(ItemStack item : items) if(!item.isEmpty()) return false;
		
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		
		return items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		
		return ItemStackHelper.getAndSplit(items, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		
		return ItemStackHelper.getAndRemove(items, index);
	}

//	TODO
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
		ItemStack item = items.get(index);
		
		boolean flag = !stack.isEmpty() && stack.isItemEqual(item) && ItemStack.areItemStackTagsEqual(stack, item);
		
		items.set(index, stack);
		
		if(stack.getCount() > getInventoryStackLimit()) stack.setCount(getInventoryStackLimit());
		if((index == 0 || index == 1) && !flag) markDirty();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		
		if(world.getTileEntity(pos) != this) return false;
		else return player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64;
	}

	@Override
	public void clear() {
		
		items.clear();
	}

	@Override
	public void tick() {
		
		if(!world.isRemote) {
			
			if(isWorking()) {
				
				if(isValidForCooking()) workTime++;
				else {
					
					workTime = 0;
					
					markDirty();
					
					return;
				}
			} else prepareCooking();
			
			if(workTime == MAX_WORK_TIME + 1) finishCooking();
		}
	}
	
	private void prepareCooking() {
		
		if(isValidForCooking()) startCooking();
	}
	
	private boolean isValidForCooking() {
		
		return !items.get(0).isEmpty() && ItemTags.WOOL.contains(items.get(0).getItem()) && !items.get(1).isEmpty() && items.get(1).getItem() == CustomItems.NEEDLE && items.get(2).getCount() < getInventoryStackLimit();
	}
	
	private void startCooking() {
		
		workTime = 1;
	}
	
	private void finishCooking() {
		
		workTime = 0;
		
		if(items.get(2).isEmpty()) items.set(2, new ItemStack(Items.STRING));
		else items.get(2).grow(1);
		
		items.get(0).shrink(1);
		items.get(1).setDamage(items.get(1).getDamage() + 1);
		
		if(items.get(1).getDamage() == items.get(1).getMaxDamage()) items.set(1, ItemStack.EMPTY);
		
		markDirty();
	}

	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		
		items.forEach(helper::accountStack);
	}
	
	@Override
	public int[] getSlotsForFace(Direction side) {
		
		return side == Direction.DOWN ? SLOTS_DOWN : side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		
		return isItemValidForSlot(index, itemStackIn);
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		
		return index == 2;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		
		if(index == 0) return ItemTags.WOOL.contains(stack.getItem());
		if(index == 1) return stack.getItem() == CustomItems.NEEDLE;
		
		return false;
	}
	
	@Override
	protected ITextComponent getDefaultName() {
		
		return SpinnerBlock.NAME;
	}
	
	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		
		return new SpinnerContainer(id, player, this, data);
	}
}