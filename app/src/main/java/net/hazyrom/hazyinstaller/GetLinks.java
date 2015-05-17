package net.hazyrom.hazyinstaller;

// Java imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetLinks {

    // Supported devices
    public String onePlusOneDownloadROM, s3DownloadROM,
            nexus5DownloadROM,nexus6DownloadROM, tabDownloadROM, nexus4DownloadROM,
            nexus10DownloadROM, g2DownloadROM, g3DownloadROM, s2DownloadROM,
            yuDownloadROM, find7DownloadROM, m8DownloadROM, motoG2014DownloadROM,
            n7100DownloadROM, gappsDownloadLink;

    // Used to read the files
    private BufferedReader bufferedReader;
    private StringBuilder stringBuilder;
    private String line;

    // Constructor
    public GetLinks(String fileName) throws IOException {
        device(fileName);
    }

    // Get the link
    public void device(String fileName) throws IOException {
        bufferedReader = new BufferedReader(new FileReader(fileName));
        try {
            stringBuilder = new StringBuilder();
            line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
                line = bufferedReader.readLine();
            }
            String link = stringBuilder.toString();

            if (fileName.contains("nexus4")) {
                this.nexus4DownloadROM = link;
            } else if (fileName.contains("nexus5")) {
                this.nexus5DownloadROM = link;
            } else if (fileName.contains("nexus6")) {
                this.nexus6DownloadROM = link;
            } else if (fileName.contains("one")) {
                this.onePlusOneDownloadROM = link;
            } else if (fileName.contains("tab")) {
                this.tabDownloadROM = link;
            } else if (fileName.contains("gapps")) {
                this.gappsDownloadLink = link;
            } else if (fileName.contains("s3")) {
                this.s3DownloadROM = link;
            } else if (fileName.contains("g2")) {
                this.g2DownloadROM = link;
            } else if (fileName.contains("g3")) {
                this.g3DownloadROM = link;
            } else if (fileName.contains("nexus10")) {
                this.nexus10DownloadROM = link;
            } else if (fileName.contains("s2")) {
                this.s2DownloadROM = link;
            } else if (fileName.contains("yu")) {
                this.yuDownloadROM = link;
            } else if (fileName.contains("m8")) {
                this.m8DownloadROM = link;
            } else if (fileName.contains("motoG2014")) {
                this.motoG2014DownloadROM = link;
            } else if (fileName.contains("n7100")) {
                this.n7100DownloadROM = link;
            }
        } finally {
            bufferedReader.close();
        }
    }
}
