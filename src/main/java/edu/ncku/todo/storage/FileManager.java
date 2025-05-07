package edu.ncku.todo.storage;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.ncku.todo.model.Config;
import edu.ncku.todo.model.AllowedValues;

public class FileManager {
    public static boolean loadConfig(Config config) {
        String cfg_path = "./data/config.json";

        Gson gson = new GsonBuilder().create();
        File file = new File(cfg_path);

        if (!file.getParentFile().exists()) {
            System.out.println("Config directory not found, creating a new one.");
            file.getParentFile().mkdirs();
        }

        try  {
            if (file.createNewFile()) {
                System.out.println("Config file not found, creating a new one.");
                return false;
            }

            Reader reader = new FileReader(file);
            // convert the JSON data to a Java object
            Config tmp = gson.fromJson(reader, Config.class);
            reader.close();

            if (tmp == null || !AllowedValues.SUPPORTED_LANGUAGES.contains(tmp.getLang())) {
                System.out.println("Config file is corrupted, set to default value.");
                return false;
            }
            
            config.setLang(tmp.getLang());
            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
