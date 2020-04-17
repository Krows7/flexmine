package net.krows_team.flexmine.items;

import static net.minecraft.item.CrossbowItem.getChargeTime;
import static net.minecraft.item.CrossbowItem.isCharged;
import static net.minecraft.item.CrossbowItem.setCharged;

import java.util.function.Predicate;

import net.krows_team.flexmine.entities.BulletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Musket extends ShootableItem {
	
	public final static Predicate<ItemStack> BULLETS = item -> item.getItem() == Items.GUNPOWDER;
	
	public Musket(Properties properties) {
		
		super(properties);
		
		addPropertyOverride(new ResourceLocation("pull"), (item, world, entity) -> {
			
			if(entity != null && item.getItem() == this) return isCharged(item) ? 0.0F : (float) (item.getUseDuration() - entity.getItemInUseCount()) / (float) getChargeTime(item);
			else return 0.0F;
		});
		addPropertyOverride(new ResourceLocation("pulling"), (item, world, entity) -> {
			
			return entity != null && entity.isHandActive() && entity.getActiveItemStack() == item && !isCharged(item) ? 1.0F : 0.0F;
		});
		addPropertyOverride(new ResourceLocation("charged"), (item, world, entity) -> {
			
			return entity != null && isCharged(item) ? 1.0F : 0.0F;
		});
	}

	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		
		return BULLETS;
	}
	
	@Override
	public boolean isCrossbow(ItemStack stack) {
		
		return stack.getItem() == this;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		
		ItemStack item = playerIn.getHeldItem(handIn);
		
		if(isCharged(item)) {
			
			shoot(worldIn, playerIn, handIn, item, 7.5F, 1.0F);
			setCharged(item, false);
			
			return new ActionResult<>(ActionResultType.SUCCESS, item);
		}
		if(playerIn.getItemInUseCount() >= getUseDuration(item)) return new ActionResult<ItemStack>(ActionResultType.SUCCESS, item);
		else if(hasStack(Items.GUNPOWDER, playerIn) && hasStack(Items.IRON_NUGGET, playerIn)) {
			
			if(!isCharged(item)) playerIn.setActiveHand(handIn);
			
			return new ActionResult<ItemStack>(ActionResultType.SUCCESS, item);	
		} else return new ActionResult<ItemStack>(ActionResultType.FAIL, item);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		
		int left = getUseDuration(stack) - timeLeft;
		
		float charge = getCharge(left, stack);
		
		if(charge >= 1.0F && !isCharged(stack) && hasAmmo(entityLiving, stack)) {
			
			setCharged(stack, true);
			
			worldIn.playSound(null, entityLiving.getPosition(), SoundEvents.ITEM_CROSSBOW_LOADING_END, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));
		}
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		
		return UseAction.CROSSBOW;
	}
	
	private static float getCharge(int useTime, ItemStack stack) {
		
		float f = (float) useTime / (float) getChargeTime(stack);
		
		return f > 1.0F ? 1.0F : f;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		
		return getChargeTime(stack) + 3;
	}
	
	private static boolean hasAmmo(LivingEntity entityIn, ItemStack stack) {
		
		if(entityIn instanceof PlayerEntity && ((PlayerEntity) entityIn).abilities.isCreativeMode) return true;
		
		ItemStack gunpowder = findStack(Items.GUNPOWDER, (PlayerEntity) entityIn);
		ItemStack bullet = findStack(Items.IRON_NUGGET, (PlayerEntity) entityIn);
		
		if(gunpowder.isEmpty() || bullet.isEmpty()) return false;
		
		gunpowder.shrink(1);
		
		bullet.shrink(1);
		
		if(gunpowder.isEmpty()) ((PlayerEntity) entityIn).inventory.deleteStack(gunpowder);
		if(bullet.isEmpty()) ((PlayerEntity) entityIn).inventory.deleteStack(bullet);
		
		return true;
		
//		ItemStack item = entityIn.findAmmo(stack);
//		
//		if(item.isEmpty()) return false;
//		
//		item.shrink(1);
//		
//		if(item.isEmpty() && entityIn instanceof PlayerEntity) ((PlayerEntity) entityIn).inventory.deleteStack(item);
//		
//		return true;
	}
	
	private static boolean hasStack(Item item, PlayerEntity player) {
		
		return findStack(item, player) != ItemStack.EMPTY;
	}
	
	private static ItemStack findStack(Item item, PlayerEntity player) {
		
		for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
			
			ItemStack stack = player.inventory.getStackInSlot(i);
			
			if(stack.getItem() == item) return stack;
		}
		
		return ItemStack.EMPTY;
	}
	
	private void shoot(World world, LivingEntity shooter, Hand hand, ItemStack item, float velocity, float inaccurancy) {
		
		if(!world.isRemote) {
			
			BulletEntity bullet = BulletEntity.create(world, shooter);
			bullet.setDamage(1.0);
			bullet.shoot(shooter, shooter.rotationPitch, shooter.rotationYaw, 0.0F, velocity, inaccurancy);
			
			world.addEntity(bullet);
			world.playSound(null, shooter.getPosition(), getShootSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));
		} else {
			
			double yaw = shooter.rotationYaw;
			double addition = 1.0;
			
			double xOff = - Math.sin(Math.toRadians(yaw)) * addition;
			double zOff = Math.cos(Math.toRadians(yaw)) * addition;
			
			Vec3d pos = shooter.getPositionVec();
			
			world.addParticle(ParticleTypes.EXPLOSION, pos.x + xOff, pos.y + 1.1, pos.z + zOff, 0.0, 0.0, 0.0);
		}
	}
	
	private SoundEvent getShootSound() {
		
		return SoundEvents.ENTITY_GENERIC_EXPLODE;
	}
}