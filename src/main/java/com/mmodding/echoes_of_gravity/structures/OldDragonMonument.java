package com.mmodding.echoes_of_gravity.structures;

import com.mmodding.mmodding_lib.library.structures.CustomStructure;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.StructurePiecesGeneratorFactory;
import net.minecraft.structure.piece.PoolStructurePiece;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.Optional;

public class OldDragonMonument extends CustomStructure<StructurePoolFeatureConfig> {

	public static final Codec<StructurePoolFeatureConfig> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(StructurePoolFeatureConfig::getStartPool),
					Codec.intRange(0, 30).fieldOf("size").forGetter(StructurePoolFeatureConfig::getSize)
			).apply(instance, StructurePoolFeatureConfig::new)
	);

	public OldDragonMonument() {
		super(CODEC, OldDragonMonument::createPiecesGenerator, PostPlacementProcessor.EMPTY);
	}

	private static boolean isFeatureChunk(StructurePiecesGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
		return context.chunkPos().equals(new ChunkPos(6250, 6250));
	}

	public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(StructurePiecesGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
		if (!isFeatureChunk(context)) return Optional.empty();

		return StructurePoolBasedGenerator.method_30419(
				context,
				PoolStructurePiece::new,
				context.chunkPos().getCenterAtY(0),
				false,
				true
		);
	}
}
