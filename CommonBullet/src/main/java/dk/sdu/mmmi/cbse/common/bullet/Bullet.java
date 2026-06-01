package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Bullet extends Entity {
	private BulletOwner owner = BulletOwner.UNKNOWN;

	public BulletOwner getOwner() {
		return owner;
	}

	public void setOwner(BulletOwner owner) {
		this.owner = owner;
	}
}
