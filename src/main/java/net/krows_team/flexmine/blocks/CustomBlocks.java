package net.krows_team.flexmine.blocks;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import net.krows_team.flexmine.registry.FlexMineEntryContainer;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public final class CustomBlocks extends FlexMineEntryContainer<Block> {
	
	public final static CustomBlocks INSTANCE = new CustomBlocks();
	
	public final static Block SPIKES = create(SpikesBlock::new, p -> p.hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(3), Material.GLASS, "spikes");
	public final static Block LIFT = create(LiftBlock::new, p -> p.hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL).harvestTool(ToolType.PICKAXE).harvestLevel(1), Material.IRON, "lift");
	public final static Block SPINNER = create(SpinnerBlock::new, p -> p.hardnessAndResistance(1.0F, 1.0F).sound(SoundType.WOOD), Material.WOOD, "spinner");
	public final static Block COMPRESSOR = create(CompressorBlock::new, p -> p, Material.IRON, "compressor");
	public final static Block VANILLA_JUKEBOX = loadVanilla(CustomJukeboxBlock::new, p -> p.hardnessAndResistance(2.0F, 6.0F), Material.WOOD, MaterialColor.DIRT, "jukebox");
	public final static Block PIZZA = create(PizzaBlock::new, p -> p.hardnessAndResistance(0.5F).sound(SoundType.CLOTH), Material.CAKE, "pizza");
	public final static Block LIGHT_REFLECTOR = create(LightReflectorBlock::new, p -> p, Material.ROCK, "light_reflector");
	public final static Block LIGHT_DETECTOR = create(LightDetectorBlock::new, p -> p, Material.ROCK, "light_detector");
	
	private static Block create(Function<Properties, Block> block, UnaryOperator<Properties> properties, Material material, String name) {
		
		return INSTANCE.load(block.apply(properties.apply(Properties.create(material))), name);
	}
	
	private static Block loadVanilla(Function<Properties, Block> block, UnaryOperator<Properties> properties, Material material, MaterialColor color, String name) {
		
		return INSTANCE.loadVanilla(block.apply(properties.apply(Properties.create(material, color))), name);
	}
}