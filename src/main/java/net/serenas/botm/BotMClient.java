package net.serenas.botm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.FlameParticle;

public class BotMClient implements ClientModInitializer{

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(BotM.BLUE_FLAME, FlameParticle.Factory::new);



    }
    
    
}
