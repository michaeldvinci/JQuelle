/*
 * Copyright Michael D. Vinci
 * michaeldvinci | coned_miro
 */
package jquelle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *
 * @author michaeldvinci
 */
public class PackageCreation {
    
    static public void createPackage(String path)  throws  InterruptedException, IOException { 
        createTempScript2(path);
        
        String[] env = new String[] { "path=%PATH%;C:\\Windows\\System32\\" };
        String pathToFile = "/mnt/c/Users/Public/" + System.getProperty("user.name") + ".sh";
        
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(new String[] {"powershell", "-command", "bash.exe", "-c", pathToFile }, env);
        
        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
 
        String line=null;

        while((line=input.readLine()) != null) {
            System.out.println(line);
        }

        int exitVal = pr.waitFor();
        System.out.println("Exited with error code "+ exitVal);
        System.out.println("done");
        if (exitVal == 0) {
            JOptionPane.showMessageDialog(null, "Congrats, .deb created on Desktop!");
        }
    }
    
    static public void createTempScript2(String bashPath) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            String content = "#!/bin/bash \n cp -r " + bashPath + " /home/; \n cd /home/" + bashPath.substring(bashPath.lastIndexOf("/")+1,bashPath.length()) +"; \n chmod 755 DEBIAN;\ndpkg-deb -Zgzip -b /home/" + bashPath.substring(bashPath.lastIndexOf("/")+1,bashPath.length()) + " /mnt/c/Users/" + System.getProperty("user.name") + "/Desktop;";
            fw = new FileWriter("C:\\Users\\Public\\" + System.getProperty("user.name") + ".sh");
            bw = new BufferedWriter(fw);
            bw.write(content);

            System.out.println("Done");
        } catch (IOException e) {
        } finally {
            try {
                    if (bw != null)
                            bw.close();
                    if (fw != null)
                            fw.close();
            } catch (IOException ex) {
            }
        }
    }
}
