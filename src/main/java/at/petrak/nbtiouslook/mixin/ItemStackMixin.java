package at.petrak.nbtiouslook.mixin;

import at.petrak.nbtiouslook.NBTiousLookMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "setTag", at = @At("HEAD"))
    public void onSetTag(CompoundTag tag, CallbackInfo ci) {
        if (NBTiousLookMod.STACKTRACE_LEN >= 0) {
            NBTiousLookMod.LOGGER.info("Item: {}. New tag: {}", this, tag);
            if (NBTiousLookMod.STACKTRACE_LEN > 0) {
                var st = Thread.currentThread().getStackTrace();
                for (int i = SKIP_COUNT; i < Math.min(NBTiousLookMod.STACKTRACE_LEN + SKIP_COUNT, st.length); i++) {
                    NBTiousLookMod.LOGGER.info(st[i].toString());
                }
            }
        }
    }

    // thread, item stack mixin
    private static final int SKIP_COUNT = 2;
}
