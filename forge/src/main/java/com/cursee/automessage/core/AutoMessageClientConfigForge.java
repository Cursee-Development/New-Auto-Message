package com.cursee.automessage.core;

import com.cursee.automessage.Constants;
import com.cursee.automessage.core.config.AutoMessageClientValues;
import com.cursee.automessage.core.config.ClientDefaultConfiguration;
import com.cursee.monolib.platform.Services;
import com.cursee.monolib.util.toml.Toml;
import com.cursee.monolib.util.toml.TomlWriter;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AutoMessageClientConfigForge {

    public static final File CLIENT_CONFIG_DIRECTORY = new File(Services.PLATFORM.getGameDirectory() + File.separator + "config");
    public static final String CLIENT_CONFIG_FILEPATH = CLIENT_CONFIG_DIRECTORY + File.separator + Constants.MOD_ID + "-client.toml";

    public static void initialize() {

        if (!CLIENT_CONFIG_DIRECTORY.isDirectory()) CLIENT_CONFIG_DIRECTORY.mkdir();

        final File CLIENT_CONFIG_FILE = new File(CLIENT_CONFIG_FILEPATH);

        AutoMessageClientConfigForge.handle(CLIENT_CONFIG_FILE);
    }

    private static void handle(File file) {
        if (!file.isFile()) {
            try {
                TomlWriter writer = new TomlWriter();
                writer.write(new ClientDefaultConfiguration(), file);

                BufferedWriter bufferedWriter = appendComments(file);
                bufferedWriter.close();
            }
            catch (IOException e) {
                Constants.LOG.error("Fatal error occurred while attempting to write " + Constants.MOD_ID + "-client.toml");
                Constants.LOG.error("Did another process delete the config directory during writing?");
                Constants.LOG.error(e.getMessage());
            }
        }
        else {
            try {
                Toml toml = new Toml().read(file);
                AutoMessageClientValues.enabled = toml.getBoolean("enabled");
                AutoMessageClientValues.messages = toml.getList("messages");
                AutoMessageClientValues.links = toml.getList("links");
                AutoMessageClientValues.intervals = toml.getList("intervals");
                AutoMessageClientValues.pure_times = toml.getList("pure_times");
                AutoMessageClientValues.soft_limits = toml.getList("soft_limits");
                AutoMessageClientValues.hard_limits = toml.getList("hard_limits");
            }
            catch (IllegalStateException exception) {
                Constants.LOG.error("Fatal error occurred while attempting to read " + Constants.MOD_ID + "-client.toml");
                Constants.LOG.error("Did another process delete the file during reading?");
                Constants.LOG.error(exception.getMessage());
            }
        }
    }

    private static @NotNull BufferedWriter appendComments(File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.newLine();
        bufferedWriter.append(" # Every message must have a corresponding value for every config option.\n");
        bufferedWriter.append(" # In some cases, the value can be 0 or \"\", these are considered 'empty' values.\n");
        bufferedWriter.append(" # An empty value in [x] will [y]:\n");
        bufferedWriter.append(" # An empty value in links will send a message without a link attached.\n");
        bufferedWriter.append(" # An empty value in intervals will attempt to use the value from pure_times\n");
        bufferedWriter.append(" # An empty value in pure_times will attempt to use the value from intervals\n");
        bufferedWriter.append(" # An empty value in soft_limits will allow the message unlimited times per session\n");
        bufferedWriter.append(" # An empty value in hard_limits will allow unlimited messages per client");
        bufferedWriter.flush();
        return bufferedWriter;
    }
}
