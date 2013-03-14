package immunity.http.marshall;

import com.google.gson.Gson;
import immunity.data.core.Action;
import immunity.data.core.Action2;
import immunity.data.core.Empty;
import immunity.data.core.Error;
import immunity.db.Connector;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UploadMarshaller {
    private final Gson gson = new Gson();
    private final Empty empty = new Empty();

    public UploadMarshaller() {
    }
    public void marshal(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (ServletFileUpload.isMultipartContent(req)) {
                ServletFileUpload fileUpload = new ServletFileUpload();
                FileItemIterator items = fileUpload.getItemIterator(req);
                if (items.hasNext()) {
                    FileItemStream item = items.next();
                    if (!item.isFormField()) {
                        String filename = FilenameUtils.getName(item.getName());
                        InputStream filecontent = item.openStream();
                        //todo fix
                        File p = new File("C:\\tmp\\" + filename);
                        FileOutputStream out = new FileOutputStream(p);


                        int read = 0;
                        byte[] bytes = new byte[1024];

                        while ((read = filecontent.read(bytes)) != -1) {
                            out.write(bytes, 0, read);
                        }

                        filecontent.close();
                        out.flush();
                        out.close();
                    }
                    write(resp, empty, HttpServletResponse.SC_OK);
                }
            }
            write(resp, empty, HttpServletResponse.SC_BAD_REQUEST);

        } catch (Exception e) {
            Error error = new Error(e.getClass().getSimpleName(), e.getMessage());
            write(resp, error, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void write(HttpServletResponse resp, Object o, int status) {
        String json = gson.toJson(o);
        PrintWriter writer = writer(resp);
        resp.setStatus(status);
        resp.setContentType("text/json");
        writer.println(json);
    }

    private PrintWriter writer(HttpServletResponse resp) {
        try {
            return resp.getWriter();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    private String getValue(Part part) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
        StringBuilder value = new StringBuilder();
        char[] buffer = new char[1024];
        for (int length = 0; (length = reader.read(buffer)) > 0;) {
            value.append(buffer, 0, length);
        }
        return value.toString();
    }
}
