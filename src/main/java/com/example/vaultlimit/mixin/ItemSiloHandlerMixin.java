package com.example.vaultlimit.mixin;

import com.example.vaultlimit.SingleItemStackHandler;
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

@Mixin(targets = "com.hlysine.create_connected.content.itemsilo.ItemSiloBlockEntity", remap = false)
public abstract class ItemSiloHandlerMixin {

    @Shadow
    protected ItemStackHandler inventory;

    @Shadow
    protected abstract void updateComparators();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void replaceInventory(BlockEntityType<?> type, BlockPos pos, BlockState state, CallbackInfo ci) {
        this.inventory = new SingleItemStackHandler((BlockEntity) (Object) this);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void flushComparatorUpdate(CallbackInfo ci) {
        if (this.inventory instanceof SingleItemStackHandler handler && handler.consumeComparatorUpdate()) {
            updateComparators();
        }
    }
}
