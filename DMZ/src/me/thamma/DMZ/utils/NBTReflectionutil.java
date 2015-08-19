package me.thamma.DMZ.utils;

import java.lang.reflect.Method;

import org.bukkit.inventory.ItemStack;

public class NBTReflectionutil {
	@SuppressWarnings("rawtypes")
	private static Class getCraftItemstack() {
		String Version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",")
				.split(",")[3];
		try {
			return Class.forName("org.bukkit.craftbukkit." + Version + ".inventory.CraftItemStack");
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static Object getnewNBTTag() {
		String Version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",")
				.split(",")[3];
		try {
			Class c = Class.forName("net.minecraft.server." + Version + ".NBTTagCompound");
			return c.newInstance();
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	private static Object setNBTTag(Object NBTTag, Object NMSItem) {
		try {
			Method method = NMSItem.getClass().getMethod("setTag", new Class[] { NBTTag.getClass() });
			method.invoke(NMSItem, new Object[] { NBTTag });
			return NMSItem;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object getNMSItemStack(ItemStack item) {
		Class cis = getCraftItemstack();
		try {
			Method method = cis.getMethod("asNMSCopy", new Class[] { ItemStack.class });
			return method.invoke(cis, new Object[] { item });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ItemStack getBukkitItemStack(Object item) {
		Class cis = getCraftItemstack();
		try {
			Method method = cis.getMethod("asBukkitCopy", new Class[] { item.getClass() });
			Object answer = method.invoke(cis, new Object[] { item });
			return (ItemStack) answer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object getNBTTagCompound(Object nmsitem) {
		Class c = nmsitem.getClass();
		try {
			Method method = c.getMethod("getTag", new Class[0]);
			return method.invoke(nmsitem, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ItemStack setString(ItemStack item, String key, String Text) {
		Object nmsitem = getNMSItemStack(item);
		if (nmsitem == null) {
			System.out.println("Got null! (Outdated Plugin?)");
			return null;
		}
		Object nbttag = getNBTTagCompound(nmsitem);
		if (nbttag == null) {
			nbttag = getnewNBTTag();
		}
		try {
			Method method = nbttag.getClass().getMethod("setString", new Class[] { String.class, String.class });
			method.invoke(nbttag, new Object[] { key, Text });
			nmsitem = setNBTTag(nbttag, nmsitem);
			return getBukkitItemStack(nmsitem);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}

	public static String getString(ItemStack item, String key) {
		Object nmsitem = getNMSItemStack(item);
		if (nmsitem == null) {
			System.out.println("Got null! (Outdated Plugin?)");
			return null;
		}
		Object nbttag = getNBTTagCompound(nmsitem);
		if (nbttag == null) {
			nbttag = getnewNBTTag();
		}
		try {
			Method method = nbttag.getClass().getMethod("getString", new Class[] { String.class });
			return (String) method.invoke(nbttag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static ItemStack setInt(ItemStack item, String key, Integer i) {
		Object nmsitem = getNMSItemStack(item);
		if (nmsitem == null) {
			System.out.println("Got null! (Outdated Plugin?)");
			return null;
		}
		Object nbttag = getNBTTagCompound(nmsitem);
		if (nbttag == null) {
			nbttag = getnewNBTTag();
		}
		try {
			Method method = nbttag.getClass().getMethod("setInt", new Class[] { String.class, Integer.TYPE });
			method.invoke(nbttag, new Object[] { key, i });
			nmsitem = setNBTTag(nbttag, nmsitem);
			return getBukkitItemStack(nmsitem);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}

	public static Integer getInt(ItemStack item, String key) {
		Object nmsitem = getNMSItemStack(item);
		if (nmsitem == null) {
			System.out.println("Got null! (Outdated Plugin?)");
			return null;
		}
		Object nbttag = getNBTTagCompound(nmsitem);
		if (nbttag == null) {
			nbttag = getnewNBTTag();
		}
		try {
			Method method = nbttag.getClass().getMethod("getInt", new Class[] { String.class });
			return (Integer) method.invoke(nbttag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static ItemStack setDouble(ItemStack item, String key, Double d) {
		Object nmsitem = getNMSItemStack(item);
		if (nmsitem == null) {
			System.out.println("Got null! (Outdated Plugin?)");
			return null;
		}
		Object nbttag = getNBTTagCompound(nmsitem);
		if (nbttag == null) {
			nbttag = getnewNBTTag();
		}
		try {
			Method method = nbttag.getClass().getMethod("setDouble", new Class[] { String.class, Double.TYPE });
			method.invoke(nbttag, new Object[] { key, d });
			nmsitem = setNBTTag(nbttag, nmsitem);
			return getBukkitItemStack(nmsitem);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}

	public static Double getDouble(ItemStack item, String key) {
		Object nmsitem = getNMSItemStack(item);
		if (nmsitem == null) {
			System.out.println("Got null! (Outdated Plugin?)");
			return null;
		}
		Object nbttag = getNBTTagCompound(nmsitem);
		if (nbttag == null) {
			nbttag = getnewNBTTag();
		}
		try {
			Method method = nbttag.getClass().getMethod("getDouble", new Class[] { String.class });
			return (Double) method.invoke(nbttag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static ItemStack setBoolean(ItemStack item, String key, Boolean d) {
		Object nmsitem = getNMSItemStack(item);
		if (nmsitem == null) {
			System.out.println("Got null! (Outdated Plugin?)");
			return null;
		}
		Object nbttag = getNBTTagCompound(nmsitem);
		if (nbttag == null) {
			nbttag = getnewNBTTag();
		}
		try {
			Method method = nbttag.getClass().getMethod("setBoolean", new Class[] { String.class, Boolean.TYPE });
			method.invoke(nbttag, new Object[] { key, d });
			nmsitem = setNBTTag(nbttag, nmsitem);
			return getBukkitItemStack(nmsitem);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}

	public static Boolean getBoolean(ItemStack item, String key) {
		Object nmsitem = getNMSItemStack(item);
		if (nmsitem == null) {
			System.out.println("Got null! (Outdated Plugin?)");
			return null;
		}
		Object nbttag = getNBTTagCompound(nmsitem);
		if (nbttag == null) {
			nbttag = getnewNBTTag();
		}
		try {
			Method method = nbttag.getClass().getMethod("getBoolean", new Class[] { String.class });
			return (Boolean) method.invoke(nbttag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Boolean hasKey(ItemStack item, String key) {
		Object nmsitem = getNMSItemStack(item);
		if (nmsitem == null) {
			System.out.println("Got null! (Outdated Plugin?)");
			return null;
		}
		Object nbttag = getNBTTagCompound(nmsitem);
		if (nbttag == null) {
			nbttag = getnewNBTTag();
		}
		try {
			Method method = nbttag.getClass().getMethod("hasKey", new Class[] { String.class });
			return (Boolean) method.invoke(nbttag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
