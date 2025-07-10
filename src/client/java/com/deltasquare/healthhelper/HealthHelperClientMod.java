package com.deltasquare.healthhelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;

public class HealthHelperClientMod implements ClientModInitializer {

    private int soundPlayCount = 0;
    private int tickDelayCounter = 0;
    private static final int TICKS_BETWEEN_SOUNDS = 6;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            PlayerEntity player = client.player;
            float health = player.getHealth();

            if (health <= 6.0f) {
                if (soundPlayCount < 3) {
                    if (tickDelayCounter == 0) {
                        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        soundPlayCount++;
                        tickDelayCounter = TICKS_BETWEEN_SOUNDS;
                    } else {
                        tickDelayCounter--;
                    }
                }
            } else {
                soundPlayCount = 0;
                tickDelayCounter = 0;
            }
        });
    }
}
