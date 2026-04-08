package com.example.vaultlimit.mixin;

import com.simibubi.create.content.logistics.vault.ItemVaultHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ItemVaultHandler.class, remap = false)
public class ItemVaultHandlerMixin {
    @Overwrite
    public int getSlots() { return 1280; }

    @Overwrite
    public int getSlotLimit(int slot) { return 1; }
}
