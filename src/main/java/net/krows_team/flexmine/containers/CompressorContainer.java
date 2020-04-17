package net.krows_team.flexmine.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public class CompressorContainer extends Container {
	
	private IIntArray data;
	
	private IInventory inventory;
	
	private World world;
	
	protected CompressorContainer(int id, PlayerInventory inventory) {
		
		this(id, inventory, new Inventory(3), new IntArray(2));
	}
	
	public CompressorContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray data) {
		
		super(CustomContainers.COMPRESSOR, id);
		
		this.inventory = inventory;
		
		this.data = data;
		
		this.world = playerInventory.player.world;
		
//		TODO
		addSlot(new Slot(inventory, 0, 0, 0));
		addSlot(new ResultSlot(playerInventory.player, inventory, 1, 0, 0));
		
		for(int i = 0; i < 3; ++i) {
			
			for(int j = 0; j < 9; ++j) {
				
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		trackIntArray(data);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		
		ItemStack item = ItemStack.EMPTY;
		
		Slot slot = inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) {
			
			ItemStack item1 = slot.getStack();
			
			item = item1.copy();
			
			if(index == 1) {
				
				if(!mergeItemStack(item1, 2, 38, true)) return ItemStack.EMPTY;
				
				slot.onSlotChange(item1, item);
			} else if(index == 0) {
				
				if(!mergeItemStack(item1, 2, 38, false)) return ItemStack.EMPTY;
			} else if(index >= 2 && index < 30) {
				
				if(!mergeItemStack(item1, 30, 39, false)) return ItemStack.EMPTY;
			} else if(index >= 30 && index < 39 && !mergeItemStack(item1, 3, 30, false)) return ItemStack.EMPTY;
			
			if(item1.isEmpty()) slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
			
			if(item1.getCount() == item.getCount()) return ItemStack.EMPTY;
			
			slot.onTake(playerIn, item1);
		}
		
		return item;
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		
		return inventory.isUsableByPlayer(playerIn);
	}
	
	private class ResultSlot extends Slot {
		
		private PlayerEntity player;
		
		private int removeCount;
		
		public ResultSlot(PlayerEntity player, IInventory inventoryIn, int index, int xPosition, int yPosition) {
			
			super(inventoryIn, index, xPosition, yPosition);
			
			this.player = player;
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			
			return false;
		}
		
		@Override
		public ItemStack decrStackSize(int amount) {
			
			if(getHasStack()) removeCount += Math.min(amount, getStack().getCount());
			
			return super.decrStackSize(amount);
		}
		
		@Override
		public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
			
			onCrafting(stack);
			
			super.onTake(thePlayer, stack);
			
			return stack;
		}
		
		@Override
		protected void onCrafting(ItemStack stack, int amount) {
			
			removeCount += amount;
			
			onCrafting(stack);
		}
		
		@Override
		protected void onCrafting(ItemStack stack) {
			
			stack.onCrafting(player.world, player, removeCount);
			
			removeCount = 0;
		}
	}
}