/*
 * Copyright Michael D. Vinci
 * michaeldvinci | coned_miro
 */
package jquelle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import javax.swing.JFileChooser;

/**
 *
 * @author michaeldvinci
 */
public class PackageCreation {
    
    private String filePath;
    public File tempScript;
    
    public void createPackage(String path)  throws  InterruptedException, IOException { 
        tempScript = createTempScript(path);
        
        String[] env = new String[] { "path=%PATH%;C:\\Windows\\System32\\" };
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(new String[] {"powershell", "-command", "bash.exe", "-c", tempScript.toString() }, env);
        
        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
 
        String line=null;

        while((line=input.readLine()) != null) {
            System.out.println(line);
        }

        int exitVal = pr.waitFor();
        System.out.println("Exited with error code "+exitVal);
        System.out.println("done");
    }
    
    public File createTempScript(String bashPath) throws IOException {
        File tempScript = File.createTempFile("script", null);

        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(
                tempScript));
        PrintWriter printWriter = new PrintWriter(streamWriter);

        printWriter.println("#!/bin/bash");
        printWriter.println("cp -r " + bashPath + "/home/");
        printWriter.println("cd /home/" + bashPath.substring(bashPath.lastIndexOf("/")+1,bashPath.length()));
        printWriter.println("chmod 755 DEBIAN");
        printWriter.println("dpkg-deb -Zgzip -b ./ " + "/mnt/c/Users/" + System.getProperty("user.name") + "/Desktop" );
        printWriter.close();

        return tempScript;
    }
    
    public String selectFile() {
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home" + "/Desktop")));
        int result = fileChooser.showOpenDialog(null);
        
        return filePath;
    }
    
}
