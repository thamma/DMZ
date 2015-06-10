package me.thamma.DMZ.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager
{
    static final String prefix = "plugins/";
    static final String suffix = ""; //".yml"
    private YamlConfiguration config;
    private String path;

    public FileManager(String path)
    {
        this.path = path;
        try
        {
            this.config = loadFile(path);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public static List<FileManager> getFromFolder(String path)
    {
        File folder = new File("plugins/" + path);
        ArrayList al = new ArrayList();
        if (folder.exists())
        {
            File[] listOfFiles = folder.listFiles();
            File[] arrayOfFile1;
            int j = (arrayOfFile1 = listOfFiles).length;
            for (int i = 0; i < j; i++)
            {
                File file = arrayOfFile1[i];
                if (file.isFile()) {
                    al.add(new FileManager(
                            (folder.getPath() + "/" + file.getName().replaceFirst("[.][^.]+$", ""))
                                    .replaceFirst("plugins/", "")));
                }
            }
        }
        return al;
    }

    public String getPath()
    {
        return this.path;
    }

    public String getFileName()
    {
        return this.path.split("/")[(this.path.split("/").length - 1)];
    }

    public void set(String path, Object o)
    {
        this.config.set(path, o);
        save();
    }

    public boolean contains(String path)
    {
        return this.config.contains(path);
    }

    public List<String> getKeys(String path) {
        List<String> al = new ArrayList<String>();
        al.addAll(config.getConfigurationSection(path).getKeys(false));
        return al;
    }

    private YamlConfiguration loadFile(String path)
            throws IOException, InvalidConfigurationException
    {
        File file = new File("plugins/" + path + suffix);
        YamlConfiguration config;
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
            file.createNewFile();
            config = new YamlConfiguration();
            config.load(file);
        }
        else
        {
            config = new YamlConfiguration();
            config.load(file);
        }
        return config;
    }

    private void save()
    {
        try
        {
            this.config.save(new File("plugins/" + this.path + suffix));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Location getLocation(String src) {
        Location def = new Location(Bukkit.getWorld("world"), 0,0,0);
        if (this.config.contains(src)) {
            return Utils.str2loc(this.config.getString(src));
        }
        this.config.set(src, def);
        save();
        return def;
    }


    public String getString(String src, String def)
    {
        if (this.config.contains(src)) {
            return this.config.getString(src);
        }
        this.config.set(src, def);
        save();
        return def;
    }

    public String getString(String src)
    {
        String def = "";
        if (this.config.contains(src)) {
            return this.config.getString(src);
        }
        this.config.set(src, def);
        save();
        return def;
    }

    public int getInt(String src, int def)
    {
        if (this.config.contains(src)) {
            return this.config.getInt(src);
        }
        this.config.set(src, Integer.valueOf(def));
        save();
        return def;
    }

    public int getInt(String src)
    {
        int def = 0;
        if (this.config.contains(src)) {
            return this.config.getInt(src);
        }
        this.config.set(src, Integer.valueOf(def));
        save();
        return def;
    }

    public Boolean getBoolean(String src, Boolean def)
    {
        if (this.config.contains(src)) {
            return this.config.getBoolean(src);
        }
        this.config.set(src, def);
        save();
        return def;
    }

    public Boolean getBoolean(String src)
    {
        if (this.config.contains(src)) {
            return this.config.getBoolean(src);
        }
        this.config.set(src, false);
        save();
        return false;
    }

    public Double getDouble(String src, Double def)
    {
        if (this.config.contains(src)) {
            return this.config.getDouble(src);
        }
        this.config.set(src, def);
        save();
        return def;
    }

    public Double getDouble(String src)
    {
        Double def = 0.0D;
        if (this.config.contains(src)) {
            return this.config.getDouble(src);
        }
        this.config.set(src, def);
        save();
        return def;
    }

    public List<String> getStringList(String src, List<String> def)
    {
        if (this.config.contains(src)) {
            return this.config.getStringList(src);
        }
        this.config.set(src, def);
        save();
        return def;
    }

    public List<String> getStringList(String src)
    {
        ArrayList def = new ArrayList();
        if (this.config.contains(src)) {
            return this.config.getStringList(src);
        }
        this.config.set(src, def);
        save();
        return def;
    }
}