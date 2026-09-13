package com.example.vaultlimit;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Inventory of a single vault block: 1280 slots holding 1 item each.
 *
 * Moving a stack touches one slot per item, so comparator updates are not sent
 * from onContentsChanged. They are flagged here and flushed once per tick by the
 * block entity mixins instead.
 */
public class SingleItemStackHandler extends ItemStackHandler {

    public static final int SLOTS = 1280;

    private final BlockEntity blockEntity;
    private boolean comparatorUpdatePending;

    public SingleItemStackHandler(BlockEntity blockEntity) {
        super(SLOTS);
        this.blockEntity = blockEntity;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        comparatorUpdatePending = true;
        if (blockEntity.getLevel() != null) {
            blockEntity.getLevel().blockEntityChanged(blockEntity.getBlockPos());
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    /** Returns true once per batch of changes, clearing the pending flag. */
    public boolean consumeComparatorUpdate() {
        boolean pending = comparatorUpdatePending;
        comparatorUpdatePending = false;
        return pending;
    }
}
