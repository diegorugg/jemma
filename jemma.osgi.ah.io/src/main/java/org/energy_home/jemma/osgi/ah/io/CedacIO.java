/**
 * This file is part of JEMMA - http://jemma.energy-home.org
 * (C) Copyright 2013 Telecom Italia (http://www.telecomitalia.it)
 *
 * JEMMA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License (LGPL) version 3
 * or later as published by the Free Software Foundation, which accompanies
 * this distribution and is available at http://www.gnu.org/licenses/lgpl.html
 *
 * JEMMA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License (LGPL) for more details.
 *
 */
package org.energy_home.jemma.osgi.ah.io;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.energy_home.jemma.osgi.ah.io.flexgateway.FlexGatewayLed2;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class CedacIO implements EventHandler {

	private static final Log log = LogFactory.getLog(CedacIO.class);

	private static final int BOOTING = 0;
	private static final int ADDING_NODE = 1;
	private static final int UPDATING = 2;
	private static final int READY = 3;
	private static final int ERROR = 4;
	private static final int IDLE = 5;
	private static final int IDENTIFYING = 6;

	private boolean guiOn = false;
	private boolean platformOn = false;
	private boolean zigbeeOn = false;
	private boolean provisioningError = false;
	private boolean provisioning = false;
	private boolean networkOpen = false;
	private boolean identify = false;

	private boolean booting = false;
	private ServiceRegistration sr = null;
	private int state;

	private int previousState;

	public void start(BundleContext bc) {
		String[] topics = new String[] { "ah/provision/BEGIN", "org/osgi/framework/ServiceEvent/*",
				"org/osgi/framework/BundleEvent/*", "ah/START_IDENTIFY" };

		Dictionary props = new Hashtable();
		props.put(EventConstants.EVENT_TOPIC, topics);
		sr = bc.registerService(EventHandler.class.getName(), this, props);
		this.activate(bc);
	}

	public void stop(BundleContext bc) {
		if (sr != null) {
			sr.unregister();
		}
		this.deactivate();
	}

	protected void activate(BundleContext bc) {
		// gets the current status
		changeState(BOOTING);

		Bundle[] bundles = bc.getBundles();
		for (int i = 0; i < bundles.length; i++) {
			String symbolicName = bundles[i].getSymbolicName();
			if (symbolicName.equals("org.energy_home.jemma.osgi.ah.webui.energyathome")) {
				synchronized (this) {
					this.guiOn = true;
					handleStateChanged();
					continue;
				}
			}
		}

	}

	protected void deactivate() {
		log.debug("deactivating");
		changeState(IDLE);
	}

	protected void bindService(Object service) {
		Class[] interfaces = service.getClass().getInterfaces();
		if (matchInterface(interfaces, "org.energy_home.jemma.ah.m2m.device.M2MNetworkScl")) {
			synchronized (this) {
				platformOn = true;
				handleStateChanged();
			}
		} else if (matchInterface(interfaces, "org.energy_home.jemma.zgd.GatewayInterface")) {
			synchronized (this) {
				zigbeeOn = true;
				handleStateChanged();
			}
		}
	}

	protected void unbindService(Object service) {
		Class[] interfaces = service.getClass().getInterfaces();
		if (matchInterface(interfaces, "org.energy_home.jemma.ah.m2m.device.M2MNetworkScl")) {
			synchronized (this) {
				platformOn = false;
				handleStateChanged();
			}
		} else if (matchInterface(interfaces, "org.energy_home.jemma.zgd.GatewayInterface")) {
			synchronized (this) {
				zigbeeOn = false;
				handleStateChanged();
			}
		}
	}

	public void handleEvent(Event event) {
		if (event.getTopic().equals("org/osgi/framework/BundleEvent/STARTED")) {
			Object bundle = (Object) event.getProperty("bundle");
			if (bundle.toString().startsWith("org.energy_home.jemma.osgi.ah.webui.energyathome")) {
				synchronized (this) {
					this.guiOn = true;
					handleStateChanged();
				}
			}
		} else if (event.getTopic().equals("org/osgi/framework/BundleEvent/STOPPED")) {
			Object bundle = (Object) event.getProperty("bundle");
			if (bundle.toString().startsWith("org.energy_home.jemma.osgi.ah.webui.energyathome")) {
				synchronized (this) {
					this.guiOn = false;
					handleStateChanged();
				}
			}
		} else if (event.getTopic().equals("ah/provision/BEGIN")) {
			provisioningError = false;
			synchronized (this) {
				provisioning = true;
				handleStateChanged();
			}
		} else if (event.getTopic().equals("ah/provision/END")) {
			provisioningError = false;
			synchronized (this) {
				provisioning = false;
				handleStateChanged();
			}

		} else if (event.getTopic().equals("ah/provision/ERROR")) {
			synchronized (this) {
				provisioningError = true;
				handleStateChanged();
			}

		} else if (event.getTopic().equals("org/osgi/framework/FrameworkEvent/STARTED")) {
			this.booting = false;
			handleStateChanged();
		} else if (event.getTopic().equals("ah/zigbee/OPEN_NETWORK")) {
			synchronized (this) {
				networkOpen = true;
				handleStateChanged();
			}

		} else if (event.getTopic().equals("ah/zigbee/CLOSE_NETWORK")) {
			synchronized (this) {
				networkOpen = false;
				handleStateChanged();
			}
		} else if (event.getTopic().equals("ah/START_IDENTIFY")) {
			synchronized (this) {
				identify = true;
				handleStateChanged();
			}
		} else if (event.getTopic().equals("ah/END_IDENTIFY")) {
			synchronized (this) {
				identify = false;
				handleStateChanged();
			}
		} else if (event.getTopic().equals("ah/identify/START")) {
			synchronized (this) {
				identify = true;
				handleStateChanged();
			}
		} else if (event.getTopic().equals("ah/identify/END")) {
			synchronized (this) {
				identify = false;
				handleStateChanged();
			}
		}
	}

	private void handleStateChanged() {
		if (identify) {
			if (this.getCurrentState() != IDENTIFYING) {
				this.previousState = this.getCurrentState();
				this.changeState(IDENTIFYING);
			}
			return;
		}

		if (this.provisioning) {
			this.changeState(UPDATING);
		} else if (this.networkOpen) {
			this.changeState(ADDING_NODE);
		} else if (this.guiOn && this.zigbeeOn) {
			if (this.provisioningError) {
				this.changeState(ERROR);
			} else {
				this.changeState(READY);
			}
		} else if (booting) {
			this.changeState(BOOTING);
		} else {
			this.changeState(ERROR);
		}
	}

	private void changeState(int state) {
		switch (state) {

		case BOOTING:
			FlexGatewayLed2.setRgbLedOnCedac(Color.BLUE, false, false, false, 0, 2);
			break;

		case UPDATING:
			FlexGatewayLed2.setRgbLedOnCedac(Color.RED, false, false, false, 0, 2);
			break;

		case ADDING_NODE:
			FlexGatewayLed2.setRgbLedOnCedac(Color.BLUE, true, true, true, 0, 0);
			break;

		case READY:
			FlexGatewayLed2.setRgbLedOnCedac(Color.BLUE, false, false, false, 0, 0);
			break;

		case ERROR:
			FlexGatewayLed2.setRgbLedOnCedac(Color.RED, false, false, false, 0, 0);
			break;

		case IDLE:
			FlexGatewayLed2.setRgbLedOnCedac(Color.BLACK, false, false, false, 0, 1);
			break;

		case IDENTIFYING:
			FlexGatewayLed2.setRgbLedOnCedac(Color.GREEN, false, false, false, 2, 2);
			break;

		default:
			FlexGatewayLed2.setRgbLedOnCedac(Color.BLACK, false, false, false, 0, 1);
			break;
		}

		this.state = state;
	}

	private int getCurrentState() {
		return state;
	}

	private boolean matchClass(String[] array, String value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	private boolean matchInterface(Class[] interfaces, String ifname) {
		for (int i = 0; i < interfaces.length; i++) {
			if (interfaces[i].getName().equals(ifname)) {
				return true;
			}
		}
		return false;
	}
}
