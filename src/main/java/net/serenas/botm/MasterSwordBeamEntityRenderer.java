package net.serenas.botm;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;

public class MasterSwordBeamEntityRenderer extends EntityRenderer<MasterSwordBeamEntity> {

    protected MasterSwordBeamEntityRenderer(Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(MasterSwordBeamEntity entity) {
        return new Identifier("botm", "textures/entity/master_sword_beam/master_sword_beam.png");
    }
    
}
