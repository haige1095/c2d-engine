package info.u250.c2d.engine.resources.rules;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.resources.AliasResourceManager.LoadResourceRule;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
/**
 * @author lycying@gmail.com
 */
public class RuleParticleEffect  implements LoadResourceRule{
	@Override
	public boolean match(FileHandle file) {
		boolean result = file.extension().equals("p");
		if(result) Engine.getAssetManager().load(file.path().replace("\\", "/"),ParticleEffect.class);
		return  result;
	}
}
