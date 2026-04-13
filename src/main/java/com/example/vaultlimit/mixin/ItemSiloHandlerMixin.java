package com.example.vaultlimit.mixin;

import com.hlysine.create_connected.content.itemsilo.ItemSiloBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ItemSiloBlockEntity.class, remap = false)
public abstract class ItemSiloHandlerMixin {

    @Shadow
    protected ItemStackHandler inventory;

    @Shadow
    protected abstract void updateComparators();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void replaceInventory(BlockEntityType<?> type, BlockPos pos, BlockState state, CallbackInfo ci) {
        ItemSiloHandlerMixin self = this;
        this.inventory = new ItemStackHandler(1280) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                self.updateComparators();
                BlockEntity be = (BlockEntity) (Object) self;
                if (be.getLevel() != null) {
                    be.getLevel().blockEntityChanged(be.getBlockPos());
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }
}
