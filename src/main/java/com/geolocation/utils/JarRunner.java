package com.geolocation.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JarRunner {
    private String jarPath;

    public String runJarWithArgs(String... args) throws Exception {
        this.jarPath = findJarInTarget();
        if (this.jarPath == null) {
            throw new RuntimeException("No JAR file found in the target directory.");
        }

        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-jar");
        command.add(jarPath);
        for (String arg : args) {
            command.add(arg);
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        process.waitFor();
        return output.toString();
    }

    private String findJarInTarget() {
        File targetDir = new File("target");
        if (targetDir.exists() && targetDir.isDirectory()) {
            FilenameFilter jarFilter = (dir, name) -> name.matches("geolocation-exercise-.*SNAPSHOT(-shaded)?.jar");
            File[] jarFiles = targetDir.listFiles(jarFilter);
            if (jarFiles != null && jarFiles.length > 0) {
                // Prefer the shaded JAR if available
                for (File jar : jarFiles) {
                    if (jar.getName().contains("shaded")) {
                        return jar.getAbsolutePath();
                    }
                }
                // Fallback to the first JAR found if no shaded JAR is present
                return jarFiles[0].getAbsolutePath();
            }
        }
        return null;
    }
}
