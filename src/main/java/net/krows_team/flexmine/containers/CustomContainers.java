package net.krows_team.flexmine.containers;

import net.krows_team.flexmine.registry.FlexMineEntryContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

public class CustomContainers extends FlexMineEntryContainer<ContainerType<?>> {
	
	public final static CustomContainers INSTANCE = new CustomContainers();
	
	public final static ContainerType<SpinnerContainer> SPINNER = create(SpinnerContainer::new, "spinner");
	public final static ContainerType<SpinnerContainer> COMPRESSOR = create(CompressorContainer::new, "compressor");
	
	@SuppressWarnings("unchecked")
	private static <T extends ContainerType<?>> T create(ContainerType.IFactory<? extends Container> factory, String name) {
		
		return (T) INSTANCE.load(new ContainerType<>(factory), name);
	}
}
