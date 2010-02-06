package games.stendhal.server.entity.mapstuff.sound;

import games.stendhal.common.constants.SoundLayer;

/**
 * Plays an ambient sound in a loop.
 *
 * @author hendrik
 */
public class LoopedAmbientSoundSource extends LoopedSoundSource {

	/**
	 * Create an ambient sound area.
	 */
	public LoopedAmbientSoundSource(String sound, int radius, int volume) {
		super(sound, radius, volume, SoundLayer.AMBIENT_SOUND);
	}

}
