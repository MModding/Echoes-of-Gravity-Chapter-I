package com.mmodding.echoes_of_gravity.client;

import com.mmodding.echoes_of_gravity.Utils;
import com.mmodding.echoes_of_gravity.events.OldDragonMonumentCallback;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class ClientPacketReceivers implements ClientElementsInitializer {

	@Override
	public void registerClient() {
		ClientPlayNetworking.registerGlobalReceiver(Utils.newIdentifier("biomeupdate"), (client, handler, buf, responseSender) -> {
			OldDragonMonumentCallback.EVENT.invoker().interact(client.world, buf.readBlockPos());
			client.execute(client.worldRenderer::reloadTransparencyShader);
			client.worldRenderer.reload(client.getResourceManager());
		});
	}
}
