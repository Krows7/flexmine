package net.krows_team.flexmine.tile_entities;

import net.krows_team.flexmine.FlexMine;
import net.krows_team.flexmine.blocks.CustomBlocks;
import net.krows_team.flexmine.registry.MyLocalModEntryContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityType.Builder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class CustomTileEntities extends MyLocalModEntryContainer<TileEntityType<?>> {
	
	public final static CustomTileEntities INSTANCE = new CustomTileEntities();
	
	public final static TileEntityType<SpinnerTileEntity> SPINNER = create(TileEntityType.Builder.create(SpinnerTileEntity::new, CustomBlocks.SPINNER), "spinner");
	public final static TileEntityType<CustomJukeboxTileEntity> VANILLA_JUKEBOX = create(TileEntityType.Builder.create(CustomJukeboxTileEntity::new, CustomBlocks.VANILLA_JUKEBOX), "jukebox");
	public final static TileEntityType<LightDetectorTileEntity> LIGHT_DETECTOR = create(TileEntityType.Builder.create(LightDetectorTileEntity::new, CustomBlocks.LIGHT_DETECTOR), "light_detector");
	
	private static <T extends TileEntity> TileEntityType<T> create(Builder<T> builder, String name) {
		
//		Type<?> type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, name);
		
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, new ResourceLocation(FlexMine.MOD_ID, name), builder.build(null));
		
//		return (TileEntityType<T>) INSTANCE.load(builder.build(null), name);
	}
}