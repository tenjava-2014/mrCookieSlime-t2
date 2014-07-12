package com.tenjava.entries.mrCookieSlime.t2;

import java.util.ArrayList;
import java.util.List;

public enum InfusionSet {
	
	WHIRLWIND,
	FIREBALL,
	ICEBALL,
	CANNON, 
	EXTINGUISH,
	ENDER_HAND,
	SHURIKEN;
	
	public boolean isContainedin(List<Infusion> list) {
		return list.containsAll(getRequiredInfusions());
	}
	
	public List<Infusion> getRequiredInfusions() {
		List<Infusion> list = new ArrayList<Infusion>();
		
		switch(this) {
		case WHIRLWIND:
			list.add(Infusion.AIR);
			list.add(Infusion.FLUX);
			break;
		case CANNON:
			list.add(Infusion.AIR);
			list.add(Infusion.METAL);
			break;
		case FIREBALL:
			list.add(Infusion.AIR);
			list.add(Infusion.FIRE);
			list.add(Infusion.EARTH);
			break;
		case ICEBALL:
			list.add(Infusion.AIR);
			list.add(Infusion.WATER);
			break;
		case SHURIKEN:
			list.add(Infusion.AIR);
			list.add(Infusion.METAL);
			list.add(Infusion.EARTH);
			break;
		case EXTINGUISH:
			list.add(Infusion.FIRE);
			list.add(Infusion.AIR);
			list.add(Infusion.WATER);
			break;
		case ENDER_HAND:
			list.add(Infusion.FLUX);
			list.add(Infusion.METAL);
			list.add(Infusion.AIR);
			break;
		default:
			break;
		}
		
		return list;
	}

}
