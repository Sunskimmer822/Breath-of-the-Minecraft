package net.serenas.botm;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.world.World;

public class MasterSwordBeamEntity extends ExplosiveProjectileEntity {

    protected MasterSwordBeamEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
}