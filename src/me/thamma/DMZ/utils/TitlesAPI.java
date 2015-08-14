package me.thamma.DMZ.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TitlesAPI
        extends JavaPlugin
        implements Listener {
    public static boolean works = true;
    public static String nmsver;

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
    }

    boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Deprecated
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        sendTitle(player, fadeIn, stay, fadeOut, message, null);
    }

    @Deprecated
    public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
        sendTitle(player, fadeIn, stay, fadeOut, null, message);
    }

    @Deprecated
    public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        sendTitle(player, 20, 50, 20, title, subtitle);
    }

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
        connection.sendPacket(packetPlayOutTimes);
        if (subtitle != null) {
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
            connection.sendPacket(packetPlayOutSubTitle);
        }
        if (title != null) {
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatColor.translateAlternateColorCodes('&', title);
            IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
            connection.sendPacket(packetPlayOutTitle);
        }
    }

    /* Error */
    public static void sendTabTitle(Player player, String header, String footer) {
        // Byte code:
        //   0: aload_1
        //   1: ifnonnull +6 -> 7
        //   4: ldc -52
        //   6: astore_1
        //   7: bipush 38
        //   9: aload_1
        //   10: invokestatic 157	org/bukkit/ChatColor:translateAlternateColorCodes	(CLjava/lang/String;)Ljava/lang/String;
        //   13: astore_1
        //   14: aload_2
        //   15: ifnonnull +6 -> 21
        //   18: ldc -52
        //   20: astore_2
        //   21: bipush 38
        //   23: aload_2
        //   24: invokestatic 157	org/bukkit/ChatColor:translateAlternateColorCodes	(CLjava/lang/String;)Ljava/lang/String;
        //   27: astore_2
        //   28: aload_1
        //   29: ldc -110
        //   31: aload_0
        //   32: invokeinterface 148 1 0
        //   37: invokevirtual 153	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   40: astore_1
        //   41: aload_2
        //   42: ldc -110
        //   44: aload_0
        //   45: invokeinterface 148 1 0
        //   50: invokevirtual 153	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   53: astore_2
        //   54: aload_0
        //   55: checkcast 113	org/bukkit/craftbukkit/v1_8_R2/entity/CraftPlayer
        //   58: invokevirtual 115	org/bukkit/craftbukkit/v1_8_R2/entity/CraftPlayer:getHandle	()Lnet/minecraft/server/v1_8_R2/EntityPlayer;
        //   61: getfield 119	net/minecraft/server/v1_8_R2/EntityPlayer:playerConnection	Lnet/minecraft/server/v1_8_R2/PlayerConnection;
        //   64: astore_3
        //   65: new 163	java/lang/StringBuilder
        //   68: dup
        //   69: ldc -91
        //   71: invokespecial 167	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   74: aload_1
        //   75: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   78: ldc -82
        //   80: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   83: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   86: invokestatic 179	net/minecraft/server/v1_8_R2/IChatBaseComponent$ChatSerializer:a	(Ljava/lang/String;)Lnet/minecraft/server/v1_8_R2/IChatBaseComponent;
        //   89: astore 4
        //   91: new 163	java/lang/StringBuilder
        //   94: dup
        //   95: ldc -91
        //   97: invokespecial 167	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
        //   100: aload_2
        //   101: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   104: ldc -82
        //   106: invokevirtual 170	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   109: invokevirtual 176	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   112: invokestatic 179	net/minecraft/server/v1_8_R2/IChatBaseComponent$ChatSerializer:a	(Ljava/lang/String;)Lnet/minecraft/server/v1_8_R2/IChatBaseComponent;
        //   115: astore 5
        //   117: new 206	net/minecraft/server/v1_8_R2/PacketPlayOutPlayerListHeaderFooter
        //   120: dup
        //   121: aload 4
        //   123: invokespecial 208	net/minecraft/server/v1_8_R2/PacketPlayOutPlayerListHeaderFooter:<init>	(Lnet/minecraft/server/v1_8_R2/IChatBaseComponent;)V
        //   126: astore 6
        //   128: aload 6
        //   130: invokevirtual 49	java/lang/Object:getClass	()Ljava/lang/Class;
        //   133: ldc -45
        //   135: invokevirtual 213	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
        //   138: astore 7
        //   140: aload 7
        //   142: iconst_1
        //   143: invokevirtual 217	java/lang/reflect/Field:setAccessible	(Z)V
        //   146: aload 7
        //   148: aload 6
        //   150: aload 5
        //   152: invokevirtual 223	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
        //   155: goto +30 -> 185
        //   158: astore 7
        //   160: aload 7
        //   162: invokevirtual 227	java/lang/Exception:printStackTrace	()V
        //   165: aload_3
        //   166: aload 6
        //   168: invokevirtual 140	net/minecraft/server/v1_8_R2/PlayerConnection:sendPacket	(Lnet/minecraft/server/v1_8_R2/Packet;)V
        //   171: goto +20 -> 191
        //   174: astore 8
        //   176: aload_3
        //   177: aload 6
        //   179: invokevirtual 140	net/minecraft/server/v1_8_R2/PlayerConnection:sendPacket	(Lnet/minecraft/server/v1_8_R2/Packet;)V
        //   182: aload 8
        //   184: athrow
        //   185: aload_3
        //   186: aload 6
        //   188: invokevirtual 140	net/minecraft/server/v1_8_R2/PlayerConnection:sendPacket	(Lnet/minecraft/server/v1_8_R2/Packet;)V
        //   191: return
        // Line number table:
        //   Java source line #94	-> byte code offset #0
        //   Java source line #95	-> byte code offset #4
        //   Java source line #97	-> byte code offset #7
        //   Java source line #98	-> byte code offset #14
        //   Java source line #99	-> byte code offset #18
        //   Java source line #101	-> byte code offset #21
        //   Java source line #103	-> byte code offset #28
        //   Java source line #104	-> byte code offset #41
        //   Java source line #106	-> byte code offset #54
        //   Java source line #107	-> byte code offset #65
        //   Java source line #108	-> byte code offset #91
        //   Java source line #109	-> byte code offset #117
        //   Java source line #112	-> byte code offset #128
        //   Java source line #113	-> byte code offset #140
        //   Java source line #114	-> byte code offset #146
        //   Java source line #115	-> byte code offset #155
        //   Java source line #116	-> byte code offset #158
        //   Java source line #118	-> byte code offset #160
        //   Java source line #122	-> byte code offset #165
        //   Java source line #121	-> byte code offset #174
        //   Java source line #122	-> byte code offset #176
        //   Java source line #123	-> byte code offset #182
        //   Java source line #122	-> byte code offset #185
        //   Java source line #124	-> byte code offset #191
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	192	0	player	Player
        //   0	192	1	header	String
        //   0	192	2	footer	String
        //   64	122	3	connection	PlayerConnection
        //   89	33	4	tabTitle	IChatBaseComponent
        //   115	36	5	tabFoot	IChatBaseComponent
        //   126	61	6	headerPacket	net.minecraft.server.v1_8_R2.PacketPlayOutPlayerListHeaderFooter
        //   138	9	7	field	Field
        //   158	3	7	e	Exception
        //   174	9	8	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   128	155	158	java/lang/Exception
        //   128	165	174	finally
    }

    public static void sendActionBar(Player player, String message) {
        try {
            Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            Object p = c1.cast(player);
            Object ppoc = null;
            Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            if ((nmsver.equalsIgnoreCase("v1_8_R1")) || (!nmsver.startsWith("v1_8_"))) {
                Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
                Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                Method m3 = c2.getDeclaredMethod("a", new Class[]{String.class});
                Object cbc = c3.cast(m3.invoke(c2, new Object[]{"{\"text\": \"" + message + "\"}"}));
                ppoc = c4.getConstructor(new Class[]{c3, Byte.TYPE}).newInstance(new Object[]{cbc, Byte.valueOf((byte) 2)});
            } else {
                Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
                Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                Object o = c2.getConstructor(new Class[]{String.class}).newInstance(new Object[]{message});
                ppoc = c4.getConstructor(new Class[]{c3, Byte.TYPE}).newInstance(new Object[]{o, Byte.valueOf((byte) 2)});
            }
            Method m1 = c1.getDeclaredMethod("getHandle", new Class[0]);
            Object h = m1.invoke(p, new Object[0]);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", new Class[]{c5});
            m5.invoke(pc, new Object[]{ppoc});
        } catch (Exception ex) {
            ex.printStackTrace();
            works = false;
        }
    }
}
