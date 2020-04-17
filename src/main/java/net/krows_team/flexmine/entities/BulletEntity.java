package net.krows_team.flexmine.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BulletEntity extends AbstractArrowEntity {
	
	public BulletEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
		
		super(type, world);
		
		init();
	}
	
	private BulletEntity(World world, LivingEntity entity) {
		
		super(CustomEntities.BULLET, entity, world);
		
		init();
	}
	
	public static BulletEntity create(World world, LivingEntity entity) {
		
		return new BulletEntity(world, entity);
	}
	
	public static BulletEntity create(World world) {
		
		return new BulletEntity(CustomEntities.BULLET, world);
	}
	
	private void init() {
		
		setHitSound(SoundEvents.BLOCK_STONE_HIT);
	}
	
	@Override
	public void tick() {
		
		if(inGround) remove();
		
		Vec3d pos = getPositionVec();
		
		world.addParticle(ParticleTypes.POOF, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
		
		super.tick();
	}
	
	@Override
	protected ItemStack getArrowStack() {
		
		return ItemStack.EMPTY;
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}