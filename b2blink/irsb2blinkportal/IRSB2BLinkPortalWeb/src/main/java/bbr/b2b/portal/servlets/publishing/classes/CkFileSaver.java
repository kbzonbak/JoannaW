package bbr.b2b.portal.servlets.publishing.classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bbr.b2b.portal.servlets.publishing.CkImagesFilter;
import cl.bbr.core.classes.utilities.BbrUtils;

public class CkFileSaver {
    private static final Log log = LogFactory.getLog(CkImagesFilter.class);

    private static final String FUNC_NO = "CKEditorFuncNum";

    private String fileBaseDir;
    private String fileDomain;
    private String[] allowFileTypeArr;

    private FileSaveManager fileSaveManager;

    public CkFileSaver (String fileBaseDir, String fileDomain, String[] allowFileTypeArr) 
    {
        this.fileBaseDir = fileBaseDir;
        if(fileBaseDir.endsWith("/")) 
        {
            StringUtils.removeEnd(fileBaseDir, "/");
        }
        if(fileBaseDir.endsWith("\\")) 
        {
            StringUtils.removeEnd(fileBaseDir, "\\");
        }

        this.fileDomain = fileDomain;
        
        if(fileDomain.endsWith("/")) 
        {
            StringUtils.removeEnd(fileDomain, "/");
        }

        this.allowFileTypeArr = allowFileTypeArr;

        fileSaveManager = new DefaultFileSaveManager();
    }


    public void saveAndReturnUrlToClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Parse the request
        try {
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            List<FileItem> items = upload.parseRequest((HttpServletRequest) request);
            // We upload just one file at the same time
            FileItem uplFile = items.get(0);
            
            String fullContextPath = BbrUtils.getInstance().getFullContextPathFromRequest(request);
            
            String errorMessage = null;
            String relUrl = null;

            if(isAllowFileType(FilenameUtils.getName(uplFile.getName()))) {
                relUrl = fileSaveManager.saveFile(uplFile, fullContextPath , fileBaseDir, fileDomain, FileType.FILE);

            }else {
                errorMessage = "Formato de archivo no permitido";
            }


            StringBuffer sb = new StringBuffer();
            sb.append("<script type=\"text/javascript\">\n");
            // Compressed version of the document.domain automatic fix script.
            // The original script can be found at [fckeditor_dir]/_dev/domain_fix_template.js
            // sb.append("(function(){var d=document.domain;while (true){try{var A=window.parent.document.domain;break;}catch(e) {};d=d.replace(/.*?(?:\\.|$)/,'');if (d.length==0) break;try{document.domain=d;}catch (e){break;}}})();\n");
            sb.append("window.parent.CKEDITOR.tools.callFunction(").append(request.getParameter(FUNC_NO)).append(", '");
            sb.append(relUrl);
            if(errorMessage != null) {
                sb.append("', '").append(errorMessage);
            }
            sb.append("');\n </script>");

            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache");
            PrintWriter out = response.getWriter();

            out.print(sb.toString());
            out.flush();
            out.close();

        } catch (FileUploadException e) {
            log.error(e);
        }
    }


    protected boolean isAllowFileType(String fileName) {
        boolean isAllow = false;
        if(allowFileTypeArr != null && allowFileTypeArr.length > 0) {
            for(String allowFileType : allowFileTypeArr) {
                if(StringUtils.equalsIgnoreCase(allowFileType, StringUtils.substringAfterLast(fileName, "."))) {
                    isAllow = true;
                    break;
                }
            }
        }else {
            isAllow = true;
        }

        return isAllow;
    }
}
