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
import org.hildan.fxgson.adapters.extras.ColorTypeAdapter;
import javafx.scene.paint.Color;

import edu.ncku.todo.model.Config;
import edu.ncku.todo.model.Task;
import edu.ncku.todo.model.Category;
import edu.ncku.todo.model.AllowedValues;

public abstract class FileManager {
    private static final String CFG_PATH = "./data/config.json";
    private static final String TASKS_PATH = "./data/tasks.json";
    private static final String CATEGORY_PATH = "./data/categories.json";
    private static Gson gson = buildGson();

    private static Gson buildGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Color.class, new ColorTypeAdapter());
        return builder.create();
    }
    
    public static boolean loadConfig() {
        File file = new File(CFG_PATH);

        if (!file.getParentFile().exists()) {
            System.out.println("Config directory not found, creating a new one.");
            file.getParentFile().mkdirs();
        }

        try {
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
                Hashtable<String, String> tmp = gson.fromJson(reader, listType);
                reader.close();
                
                // check validation of the config file 
                // TODO: optimize this part (future work)
                if (tmp == null || !AllowedValues.SUPPORTED_LANGUAGES.contains(tmp.get("lang"))) {
                    System.err.println("Config file is corrupted, set to default value.");
                    Config.set("lang", "en");
                    Writer writer = new FileWriter(file);
                    gson.toJson(Config.getCFG(), writer);
                    writer.close();
                    return false;
                }

                Config.initialize(tmp);
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

            reader = new FileReader(TASKS_PATH);
            listType = new TypeToken<List<Task>>() {}.getType();
            List<Task> tasksList = gson.fromJson(reader, listType);
            reader.close();

            for (Task item : tasksList) {
                String categoryName = item.getCategoryName();
                if (!DataManager.addTask(categoryName, item)) {
                    // Duplicate task or category not found
                    // just in case, should not happen 
                    System.err.println(Lang.get("notify.duplicateTask"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
