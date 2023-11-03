package net.serenas.botm;

import java.util.function.Predicate;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class TravelersBow extends RangedWeaponItem implements Vanishable {
    public static final int TICKS_PER_SECOND = 20;
    public static final int RANGE = 15;

    public TravelersBow(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        float pullProgress;
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity player = (PlayerEntity)user;
        boolean noUseArrowItem = player.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
        ItemStack arrowType = player.getProjectileType(stack);
        if (arrowType.isEmpty() && !noUseArrowItem) {
            return;
        }
        if (arrowType.isEmpty()) {
            arrowType = new ItemStack(Items.ARROW);
        }
        pullProgress = TravelersBow.getPullProgress(this.getMaxUseTime(stack) - remainingUseTicks);
        if ((double)pullProgress < 0.1) {
            return;
        }
        boolean arrowNonPickupable = noUseArrowItem && arrowType.isOf(Items.ARROW);
        if (!world.isClient) {
            int punchEnchantmentLevel;
            int powerEnchantmentLevel;
            ArrowItem arrowItem = (ArrowItem)(arrowType.getItem() instanceof ArrowItem ? arrowType.getItem() : Items.ARROW);
            PersistentProjectileEntity arrow = arrowItem.createArrow(world, arrowType, player);
            arrow.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, pullProgress * 3.0f, 2.0f);
            if (pullProgress == 1.0f) {
                arrow.setCritical(true);
            }
            if ((powerEnchantmentLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack)) > 0) {
                arrow.setDamage(arrow.getDamage() + (double)powerEnchantmentLevel * 0.5 + 0.5);
            }
            if ((punchEnchantmentLevel = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack)) > 0) {
                arrow.setPunch(punchEnchantmentLevel);
            }
            if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                arrow.setOnFireFor(100);
            }
            stack.damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
            if (arrowNonPickupable || player.getAbilities().creativeMode && (arrowType.isOf(Items.SPECTRAL_ARROW) || arrowType.isOf(Items.TIPPED_ARROW))) {
                arrow.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
            world.spawnEntity(arrow);
        }
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + pullProgress * 0.5f);
        if (!arrowNonPickupable && !player.getAbilities().creativeMode) {
            arrowType.decrement(1);
            if (arrowType.isEmpty()) {
                player.getInventory().removeOne(arrowType);
            }
        }
        player.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if ((f) > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        boolean bl = !user.getProjectileType(stack).isEmpty();
        if (user.getAbilities().creativeMode || bl) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return BOW_PROJECTILES;
    }

    @Override
    public int getRange() {
        return 15;
    }
}
