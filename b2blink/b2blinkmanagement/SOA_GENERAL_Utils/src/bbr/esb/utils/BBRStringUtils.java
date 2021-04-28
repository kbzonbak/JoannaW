package bbr.esb.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class BBRStringUtils {
    private static BBRStringUtils _instance;
    // For lazy initialization
    public static synchronized BBRStringUtils getInstance() {
        if (_instance == null) {
            _instance = new BBRStringUtils();
        }
        return _instance;
    }
    // Constructor
    private BBRStringUtils() {
    }
    public static String doCSVString(String[][] data, String separator, String filename) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                stringBuffer.append(data[i][j]);
                stringBuffer.append(separator);
            }
            // Eliminamos el ultimo separador y ponemos un salto de linea
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            stringBuffer.append('\n');
        }
        File file = new File(System.getProperty("ERROR_PLFILE_PATH"));
        if (!file.exists()){
            file.mkdirs();
        }
        
        FileWriter fstream = new FileWriter(System.getProperty("ERROR_PLFILE_PATH") + filename);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(stringBuffer.toString());
        out.close();
        return filename;
    }   
}
