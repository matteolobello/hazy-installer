package net.hazyrom.hazyinstaller;

import java.io.*;

public class GetLinks {

    // Supported devices
    public String onePlusOneDownloadROM, s3DownloadROM,
            nexus5DownloadROM, tabDownloadROM, nexus4DownloadROM,
            nexus10DownloadROM, g2DownloadROM, g3DownloadROM, gappsDownloadLink;

    // Used to read the files
    private BufferedReader br;
    private StringBuilder sb;
    private String line;

    // Constructor
    public GetLinks(String fileName) throws IOException {
        device(fileName);
    }

    // Get the link
    public void device(String fileName) throws IOException {
        br = new BufferedReader(new FileReader(fileName));
        try {
            sb = new StringBuilder();
            line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            String link = sb.toString();

            if (fileName.contains("nexus4")) {
                this.nexus4DownloadROM = link;
            } else if (fileName.contains("nexus5")) {
                this.nexus5DownloadROM = link;
            } else if (fileName.contains("one")) {
                this.onePlusOneDownloadROM = link;
            } else if (fileName.contains("tab")) {
                this.tabDownloadROM = link;
            } else if (fileName.contains("gapps")) {
                this.gappsDownloadLink = link;
            } else if (fileName.contains("s3")) {
                this.s3DownloadROM = link;
            } else if (fileName.contains("nexus10")) {
                this.nexus10DownloadROM = link;
            } else if (fileName.contains("g2")) {
                this.g2DownloadROM = link;
            } else if (fileName.contains("g3")) {
                this.g3DownloadROM = link;
            }
        } finally {
            br.close();
        }
    }
}
