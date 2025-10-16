package top.offsetmonkey538.tinyterrors.common.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.PacketCodecs;

import static top.offsetmonkey538.tinyterrors.common.TinyTerrors.id;

@SuppressWarnings("UnstableApiUsage")
public final class ModAttachmentTypes {
    private ModAttachmentTypes() {

    }

    public static final AttachmentType<Boolean> IS_BABY_ATTACHMENT = AttachmentRegistry.create(
            id("is_baby"),
            builder -> builder
                    .initializer(() -> false)
                    .copyOnDeath()
                    //TODO: not sure if I want the attachment to save itself? If I don't specifically *need* to save it to nbt, this would save me from a few version-specific mixins i think: .persistent(Codec.BOOL)
                    .syncWith(
                            PacketCodecs.BOOLEAN,
                            AttachmentSyncPredicate.all()
                    )
    );

    public static void initialize() {
        // Initializes by loading class
    }
}
