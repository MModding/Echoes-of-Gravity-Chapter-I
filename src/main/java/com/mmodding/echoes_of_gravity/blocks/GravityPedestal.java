package com.mmodding.echoes_of_gravity.blocks;

import com.mmodding.echoes_of_gravity.Utils;
import com.mmodding.echoes_of_gravity.init.Biomes;
import com.mmodding.echoes_of_gravity.init.Blocks;
import com.mmodding.mmodding_lib.lib.blocks.CustomBlock;
import com.mmodding.mmodding_lib.lib.utils.BiomeUtils;
import com.mmodding.mmodding_lib.lib.utils.RadiusUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DragonEggBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class GravityPedestal extends CustomBlock {

	public static final BooleanProperty HAS_DRAGON_EGG = BooleanProperty.of("has_dragon_egg");

	public GravityPedestal(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		super(settings, hasItem, itemGroup);
		this.setDefaultState(this.getDefaultState().with(HAS_DRAGON_EGG, false));
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

		RadiusUtils.forBlockPosInCubicRadius(pos, 10, (blockPos) -> BiomeUtils.changeBiomeForBlock(world, blockPos, world.getRegistryManager().get(Registry.BIOME_KEY).get(Utils.newIdentifier("gravity_space"))));

		return ActionResult.SUCCESS;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		RadiusUtils.forBlockPosInCubicRadius(pos, 10, (blockPos) -> {
			if (world.getBlockState(blockPos).getBlock() instanceof ReinforcedGravityBlock) {
				world.setBlockState(blockPos, Blocks.REINFORCED_GRAVITY_BLOCK.getDefaultState().with(
						ReinforcedGravityBlock.GRAVITY_ISOLATION,
						world.getBlockState(pos.up()).getBlock() instanceof DragonEggBlock
				), 2);
			}
		});
		return this.getDefaultState().with(HAS_DRAGON_EGG, world.getBlockState(pos.up()).getBlock() instanceof DragonEggBlock);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(HAS_DRAGON_EGG);
	}
}
