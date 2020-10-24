package dttraverse.trees;

import com.ferreusveritas.dynamictrees.ModBlocks;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import dttraverse.DynamicTreesTraverse;
import dttraverse.ModContent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import prospector.traverse.world.TraverseWorld;

import java.util.Random;

public final class SpeciesAutumnBrown extends Species {

    public SpeciesAutumnBrown(TreeFamily treeFamily) {
        super(new ResourceLocation(DynamicTreesTraverse.MODID, "autumn_brown"), treeFamily, ModContent.autumnBrownLeavesProperties);

        this.setBasicGrowingParameters(0.3f, 12.0f, upProbability, lowestBranchHeight, 0.65f);

        this.setRequiresTileEntity(true);

        this.envFactor(BiomeDictionary.Type.LUSH, 0.75f);
        this.envFactor(BiomeDictionary.Type.SPOOKY, 1.05f);
        this.envFactor(BiomeDictionary.Type.DEAD, 1.05f);

        this.generateSeed();
        this.setupStandardSeedDropping();

        this.leavesProperties.setTree(treeFamily);
    }

    @Override
    public boolean isBiomePerfect(Biome biome) {
        return isOneOfBiomes(biome, TraverseWorld.autumnalWoodedHillsBiome, TraverseWorld.autumnalWoodsBiome);
    }

    @Override
    public boolean rot (World world, BlockPos pos, int neighborCount, int radius, Random random, boolean rapid) {
        if (super.rot(world, pos, neighborCount, radius, random, rapid)) {
            if (radius > 4 && TreeHelper.isRooty(world.getBlockState(pos.down())) && world.getLightFor(EnumSkyBlock.SKY, pos) < 4) {
                world.setBlockState(pos, random.nextInt(3) == 0 ? ModBlocks.blockStates.redMushroom : ModBlocks.blockStates.brownMushroom);//Change branch to a mushroom
                world.setBlockState(pos.down(), ModBlocks.blockStates.podzol);//Change rooty dirt to Podzol
            }
            return true;
        }
        return false;
    }

}
