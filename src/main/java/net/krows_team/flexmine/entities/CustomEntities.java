package net.krows_team.flexmine.entities;

import java.util.function.Consumer;
import java.util.function.Function;

import net.krows_team.flexmine.registry.FlexMineEntryContainer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CustomEntities extends FlexMineEntryContainer<EntityType<?>> {
	
	public final static CustomEntities INSTANCE = new CustomEntities();
	
	public final static EntityType<BulletEntity> BULLET = load(Builder.create(BulletEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((e, world) -> BulletEntity.create(world)), "bullet");
	public final static EntityType<DamagedPaintingEntity> DAMAGED_PAINTING = load(Builder.<DamagedPaintingEntity>create(DamagedPaintingEntity::new, EntityClassification.MISC).size(0.5F, 0.5F), "damaged_painting");
	public final static EntityType<VillagerFishingBobberEntity> FISHING_BOBBER = load(Builder.<VillagerFishingBobberEntity>create(EntityClassification.MISC).disableSerialization().disableSummoning().size(0.25F, 0.25F), "fishing_bobber");
//	public final static EntityType<ChickenEntity> VANILLA_CHICKEN = Reflections.changeFinalField(EntityType.class, "CHICKEN", loadVanilla(ChickenEntity::new, EntityClassification.CREATURE, b -> b.size(0.4F, 0.7F), null, "chicken"));
	
	@SuppressWarnings("unchecked")
	private static <T extends Entity> EntityType<T> load(Builder<T> builder, String name) {
		
		return (EntityType<T>) INSTANCE.load(builder.build(name), name);
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Entity> EntityType<T> loadVanilla(IFactory<T> factory, EntityClassification clazz, Function<Builder<T>, Builder<T>> builder, Consumer<T> entity, String name) {
		
		IFactory<T> f = new IFactory<T>() {

			@Override
			public T create(EntityType<T> p_create_1_, World p_create_2_) {
				
				CreatureEntity t = (CreatureEntity) factory.create(p_create_1_, p_create_2_);
				t.goalSelector.addGoal(7, new NearestAttackableTargetGoal<>(t, PlayerEntity.class, true));
				t.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
				t.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
				
				return (T) t;
			}
		};
		
		return (EntityType<T>) INSTANCE.loadVanilla(builder.apply(Builder.create(f, clazz)).build(name), name);
	}
}