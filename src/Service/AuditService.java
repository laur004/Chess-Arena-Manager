package Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {

    private static final String AUDIT_FILE = "audit.csv";
    private static AuditService instance;
    private final FileWriter writer;

    private AuditService() {
        try {
            boolean exists = new java.io.File(AUDIT_FILE).exists();
            writer = new FileWriter(AUDIT_FILE, true);
            if (!exists) {
                writer.write("ACTION_NAME,TIMESTAMP\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize audit service", e);
        }
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public synchronized void log(String actionName) {
        try {
            writer.write(actionName + "," + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
