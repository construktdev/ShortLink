package de.construkter.shortlink;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class LinkService {
    private static final String FTP_SERVER = "YOUR_SERVER";
    private static final String FTP_USER = "YOUR_USER";
    private static final String FTP_PASS = "YOUR_PASSWORD";
    private static final String BASE_URL = "https://example.com";

    public String uploadShortLink(String originalLink) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        String htmlContent = generateHtml(id, originalLink);

        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(FTP_SERVER);
            ftpClient.login(FTP_USER, FTP_PASS);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            InputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8));
            boolean uploaded = ftpClient.storeFile(id + ".html", inputStream);
            inputStream.close();
            ftpClient.logout();
            ftpClient.disconnect();

            if (uploaded) {
                return BASE_URL + id;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Fehler beim Hochladen";
    }

    private String generateHtml(String id, String originalLink) {
        return "<!DOCTYPE html>\n<html>\n<head>\n<script>\nwindow.location.href = \"" + originalLink + "\";\n</script>\n</head>\n<body>\n</body>\n</html>";
    }
}

