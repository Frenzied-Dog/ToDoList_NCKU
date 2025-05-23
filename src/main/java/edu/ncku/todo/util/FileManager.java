package edu.ncku.todo.util;

import java.lang.reflect.Type;
import java.lang.IllegalStateException;
import java.util.Hashtable;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.hildan.fxgson.FxGsonBuilder;
import org.hildan.fxgson.adapters.extras.ColorTypeAdapter;
import javafx.scene.paint.Color;

import edu.ncku.todo.model.Config;
import edu.ncku.todo.model.Category;
import edu.ncku.todo.model.AllowedValues;

public abstract class FileManager {
    private static final String CFG_PATH = "./data/config.json";
    // private static final String TASKS_PATH = "./data/tasks.json";
    private static final String CATEGORY_PATH = "./data/categories.json";
    private static Gson gson = buildGson();

    // json data parser
    private static Gson buildGson() {
        GsonBuilder builder = new FxGsonBuilder().acceptNullPrimitives().builder().serializeNulls();
        builder.registerTypeAdapter(Color.class, new ColorTypeAdapter());
        return builder.setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
    }
    
    public static boolean loadConfig() {
        File file = new File(CFG_PATH);

        // check if the config directory exists
        if (!file.getParentFile().exists()) {
            System.out.println("Config directory not found, creating a new one.");
            file.getParentFile().mkdirs();
        }

        try {
            // check if the config file exists
            if (file.createNewFile()) {
                System.err.println("Config file not found, creating a new one.");
                Config.set("lang","en");
                
                Writer writer = new FileWriter(file);
                gson.toJson(Config.getCFG(), writer);
                writer.close();
                return false;
            }

            try {
                // convert the JSON data to a Java object
                Reader reader = new FileReader(file);
                Type listType = new TypeToken<Hashtable<String, String>>() {}.getType();
                Hashtable<String, String> config = gson.fromJson(reader, listType);
                reader.close();
                
                // check validation of the config file 
                // TODO: optimize this part (future work)
                if (config == null || !AllowedValues.SUPPORTED_LANGUAGES.contains(config.get("lang"))) {
                    System.err.println("Config file is corrupted, set to default value.");
                    Config.set("lang", "en");
                    Writer writer = new FileWriter(file);
                    gson.toJson(Config.getCFG(), writer);
                    writer.close();
                    return false;
                }

                Config.initialize(config);
                return true;

            } catch (IllegalStateException e) {
                System.err.println("Config file is corrupted, set to default value.");
                Config.set("lang", "en");
                Writer writer = new FileWriter(file);
                gson.toJson(Config.getCFG(), writer);
                writer.close();
                return false;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadData() {
        try {
            FileReader reader = new FileReader(CATEGORY_PATH);
            Type listType = new TypeToken<List<Category>>() {}.getType();
            List<Category> categories = gson.fromJson(reader, listType);
            reader.close();

            DataManager.initialize(categories);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveData() {
        try {
            File file = new File(CATEGORY_PATH);
            if (!file.getParentFile().exists()) {
                System.out.println("Config directory not found, creating a new one.");
                file.getParentFile().mkdirs();
            }

            // write the data to the file
            List<Category> categories = DataManager.getCategoryData();
            Writer writer = new FileWriter(file);
            Type listType = new TypeToken<List<Category>>() {}.getType();
            gson.toJson(categories, listType, writer);
            writer.close();
            
            // write the config to the file
            writer = new FileWriter(CFG_PATH);
            gson.toJson(Config.getCFG(), writer);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
