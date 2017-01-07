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

/**
 *
 * @author michaeldvinci
 */
public class JQuelle {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        String[] env = new String[] { "path=%PATH%;C:\\Windows\\System32\\" };
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(new String[] {"powershell", "-command", "bash.exe", "-c", "/mnt/c/Users/michaeldvinci/Desktop/test/DEBIAN/control.sh" }, env);
        
        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
 
        String line=null;

        while((line=input.readLine()) != null) {
            System.out.println(line);
        }

        int exitVal = pr.waitFor();
        System.out.println("Exited with error code "+exitVal);
    }
    
    public File createTempScript() throws IOException {
    File tempScript = File.createTempFile("script", null);

    Writer streamWriter = new OutputStreamWriter(new FileOutputStream(
            tempScript));
    PrintWriter printWriter = new PrintWriter(streamWriter);

    printWriter.println("#!/bin/bash");
    printWriter.println("cd bin");
    printWriter.println("ls");

    printWriter.close();

    return tempScript;
}
    
}
