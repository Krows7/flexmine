package net.krows_team.flexmine.containers;

import net.krows_team.flexmine.items.CustomItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpinnerContainer extends Container {
	
	private IInventory inventory;
	
	private PlayerEntity player;
	
	private IIntArray data;
	
	public SpinnerContainer(int id, PlayerInventory inventory) {
		
		this(id, inventory, new Inventory(3), new IntArray(2));
	}
	
	public SpinnerContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray data) {
		
		super(CustomContainers.SPINNER, id);
		
		this.inventory = inventory;
		
		this.data = data;
		
		this.player = playerInventory.player;
		
		addSlot(new Slot(inventory, 0, 56, 17));
		addSlot(new InstrumentSlot(inventory, 1, 56, 53));
		addSlot(new ResultSlot(player, inventory, 2, 116, 35));
		
		for(int i = 0; i < 3; ++i) {
			
			for(int j = 0; j < 9; ++j) {
				
				addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; ++i) {
			
			addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
		
		trackIntArray(data);
	}
	
//	@Override
//	public void onCraftMatrixChanged(IInventory inventoryIn) {
//		
//		pos.consume((world, position) -> getResult(world));
//	}
//	
//	private void getResult(World world) {
//		
//		if(world.isRemote) return;
//		
//		ItemStack item = ItemStack.EMPTY;
//		
//		if(ItemTags.WOOL.contains(craftingInventory.getStackInSlot(0).getItem())) item = new ItemStack(Items.STRING);
//		
//		resultInventory.setInventorySlotContents(0, item);
//		
//		((ServerPlayerEntity) player).connection.sendPacket(new SSetSlotPacket(windowId, 1, item));
//	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		
		return inventory.isUsableByPlayer(playerIn);
		
//		return isWithinUsableDistance(IWorldPosCallable., playerIn, CustomBlocks.SPINNER);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		
		ItemStack item = ItemStack.EMPTY;
		
		Slot slot = inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) {
			
			ItemStack item1 = slot.getStack();
			
			item = item1.copy();
			
			if(index == 2) {
				
				if(!mergeItemStack(item1, 3, 39, false)) return ItemStack.EMPTY;
				
				slot.onSlotChange(item1, item);
			} else if(index == 0 || index == 1) {
				
				if(!mergeItemStack(item1, 3, 39, false)) return ItemStack.EMPTY;
			} else if(item1.getItem() == CustomItems.NEEDLE) {
				
				if(!mergeItemStack(item1, 1, 2, false)) return ItemStack.EMPTY;
			} else if(ItemTags.WOOL.contains(item1.getItem())) {
				
				if(!mergeItemStack(item1, 0, 1, false)) return ItemStack.EMPTY;
			} else if(index >= 3 && index < 30) {
				
				if(!mergeItemStack(item1, 30, 39, false)) return ItemStack.EMPTY;
			} else if(index >= 30 && index < 39 && mergeItemStack(item1, 3, 30, false)) return ItemStack.EMPTY;
			
			if(item1.isEmpty()) slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
			
			if(item1.getCount() == item.getCount()) return ItemStack.EMPTY;
			
			slot.onTake(playerIn, item1);
		}
		
		return item;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getCookProgressionScaled() {
		
		int i = data.get(0);
		int j = data.get(1);
		
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}
	
//	@Override
//	public void onContainerClosed(PlayerEntity playerIn) {
//		
//		super.onContainerClosed(playerIn);
//		
//		pos.consume((world, pos) -> clearContainer(playerIn, world, craftingInventory));
//	}
	
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
	
	private class InstrumentSlot extends Slot {

		public InstrumentSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			
			super(inventoryIn, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			
			return stack.getItem() == CustomItems.NEEDLE;
		}
		
		@Override
		public int getItemStackLimit(ItemStack stack) {
			
			return 1;
		}
	}
}