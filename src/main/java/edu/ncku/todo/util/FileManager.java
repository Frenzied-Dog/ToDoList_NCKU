package edu.ncku.todo.util;

import java.lang.reflect.Type;
import java.lang.IllegalStateException;
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
import edu.ncku.todo.model.Config;
import edu.ncku.todo.model.Task;
import edu.ncku.todo.model.AllowedValues;
import edu.ncku.todo.model.CategoryMap;

public abstract class FileManager {
    private static final String CFG_PATH = "./data/config.json";
    private static final String TASKS_PATH = "./data/tasks.json";
    private static final String CATEGORY_PATH = "./data/categories.json";
    private static Gson gson = new GsonBuilder().create();

    public static boolean loadConfig(Config config) {
        File file = new File(CFG_PATH);

        if (!file.getParentFile().exists()) {
            System.out.println("Config directory not found, creating a new one.");
            file.getParentFile().mkdirs();
        }

        try {
            if (file.createNewFile()) {
                System.err.println("Config file not found, creating a new one.");
                config.setLang("en");

                Writer writer = new FileWriter(file);
                gson.toJson(config, Config.class, writer);
                writer.close();
                return false;
            }

            try {
                // convert the JSON data to a Java object
                Reader reader = new FileReader(file);
                Config tmp = gson.fromJson(reader, Config.class);
                reader.close();
                if (tmp == null || !AllowedValues.SUPPORTED_LANGUAGES.contains(tmp.getLang())) {
                    System.err.println("Config file is corrupted, set to default value.");
                    config.setLang("en");
                    Writer writer = new FileWriter(file);
                    gson.toJson(config, Config.class, writer);
                    writer.close();
                    return false;
                }

                config.setLang(tmp.getLang());
                return true;

            } catch (IllegalStateException e) {
                System.err.println("Config file is corrupted, set to default value.");
                config.setLang("en");
                Writer writer = new FileWriter(file);
                gson.toJson(config, Config.class, writer);
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
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> Categories = gson.fromJson(reader, listType);
            reader.close();

            for (String str : Categories) {
                CategoryMap.addCategory(str);
            }

            reader = new FileReader(TASKS_PATH);
            listType = new TypeToken<List<Task>>() {}.getType();
            List<Task> tasksList = gson.fromJson(reader, listType);
            reader.close();

            for (Task item : tasksList) {
                String categoryName = item.getCategoryName();
                if (!CategoryMap.getCategory(categoryName).contains(item)) {
                    CategoryMap.addTask(categoryName, item);
                } else {
                    System.err.println("Duplicate task found: " + item.getName() + " in category: " + categoryName);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
