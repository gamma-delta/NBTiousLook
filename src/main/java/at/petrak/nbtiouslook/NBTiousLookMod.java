package at.petrak.nbtiouslook;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NBTiousLookMod.MOD_ID)
public class NBTiousLookMod {
    public static final String MOD_ID = "nbtiouslook";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public NBTiousLookMod() {
        // For things that happen in initialization
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        // For everything else
        var evBus = MinecraftForge.EVENT_BUS;

        evBus.register(NBTiousLookMod.class);
    }

    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static int STACKTRACE_LEN = -1;

    @SubscribeEvent
    public static void command(RegisterCommandsEvent evt) {
        evt.getDispatcher().register(Commands.literal(MOD_ID)
            .then(Commands.argument("stacktraceLen", IntegerArgumentType.integer(-1, Integer.MAX_VALUE))
                .executes(ctx -> {
                    STACKTRACE_LEN = IntegerArgumentType.getInteger(ctx, "stacktraceLen");
                    ctx.getSource()
                        .sendSuccess(new TextComponent(
                            String.format("set stacktrace to %d (use -1 to disable and 0 to print no stacktrace)",
                                STACKTRACE_LEN)), true);
                    return 1;
                })));
    }
}
